var PLUS          = "_PLUS_";
var AMPERSAND     = "_AMPERSAND_";

var VISIBLE_PROGRESS = true;
var READY_STATE_UNINITIALIZATION = 0;
var READY_STATE_READING = 1;
var READY_STATE_HASREAD = 2;
var READY_STATE_COMPLETION = 4;
var RESPONSE_OK = 200;
var xmlhttpRequest = null;
var tramsmissionId = null;
var progressBar = null;
var iFiled = null;
var timeOutId = null;
var FOCUS_OFFSET_TIME = 1;
var asyncFocusIs = "";
var asyncBallon = "";
//2010/10/12 子ウィンドウを開いている時の定期送信の間隔を指定
var regularTime = 5;

/**
 * XMLHttpRequest生成
 * @return 生成したXMLHttpRequest
 */
function createXmlHttp()
{
	if (window.XMLHttpRequest) {
		// If IE7, Mozilla, Safari, and so on: Use native object.
		return new XMLHttpRequest();
	} else {
		if (window.ActiveXObject) {
			// ...otherwise, use the ActiveX control for IE5.x and IE6.
			return new ActiveXObject('MSXML2.XMLHTTP.3.0');
		} else {
			return null;
		}
	}
}

/**
 * 非同期通信PostBack
 */
function ajaxPostBack()
{
	// PostBack用のパラメータの設定
	var args = ajaxPostBack.arguments;
	var eventArgs = "";

	// 2012/07/05 v5.2.2.11 定期送信時にFormat系のテキストボックスにフォーカスがあった場合、フォーカスが外れた後にJavaScriptのフォーマットエラーメッセージが表示される不具合を修正 start
	var isPollingRequest = false;
	if (args.length > 1) {
		isPollingRequest = args[0] == "page" && args[1] == "clientPull";
	}
	// 2012/07/05 v5.2.2.11 end
	
	// ファイルブラウズ対応
	var enc = document.forms[0].enctype;
	if ( enc == 'multipart/form-data' )
	{
		var postBackArgs = "";
		for ( var i = 0; i < args.length ; i++ )
		{
			postBackArgs += ",'" + args[i] + "'";
		}
		postBackArgs = postBackArgs.substring(1);
		eval("postBack(" +  postBackArgs + " )" );
		return;
	}
	for ( var i = 1; i < args.length ; i++ )
	{
		if ( i != 1 )
		{
			eventArgs += ",";
		}
		eventArgs += args[i];
	}
	if (!canSend(args[0], eventArgs))
	{
		// 2012/04/10 5.2.2.10 PDFヘルプのオープンと定期送信が重なってしまった場合に定期送信がストップしてしまう不具合対応(２重送信チェックに引っかかった場合はfalseで判定する)
		return false;
	}
	// Mod K.fukumori 2006.11.08
	var eventElement = document.all(args[0]);
	if(event != null && event.srcElement != null)
	{
		eventElement = event.srcElement;
	}
	if ( eventElement != null &&
		 eventElement.tagName=='INPUT' &&
		 eventElement.type=='text' &&
		 eventElement.format == 'true' &&
		 eventElement.style.visibility != 'hidden' &&
		 eventElement.readOnly != true
		 && !isPollingRequest) // 2012/07/05 v5.2.2.11
	{
		enterKeyEvent = true;
		isChangeBackColor = false;
		isFocusSelect = false;
		eventElement.onblur();
		eventElement.blur(); // 2012/07/05 v5.2.2.11
	}
	// Mod END

	var activeElement = document.activeElement;
	if ( activeElement != null &&
		 activeElement.tagName=='INPUT' &&
		 activeElement.type=='text' &&
		 activeElement.format == 'true' &&
		 activeElement.style.visibility != 'hidden' &&
		 activeElement.readOnly != true 
		 && !isPollingRequest) // 2012/07/05 v5.2.2.11
	{
		enterKeyEvent = true;
		isChangeBackColor = false;
		isFocusSelect = false;
		activeElement.onblur();
		activeElement.onblur(); // 2012/07/05 v5.2.2.11
	}
	if ( xmlhttpRequest == null )
	{
		xmlhttpRequest = createXmlHttp();
	}
	else
	{
		switch( xmlhttpRequest.readyState )
		{
			case READY_STATE_UNINITIALIZATION :
				xmlhttpRequest = createXmlHttp();
				return;
			case READY_STATE_READING :
				return;
			case READY_STATE_HASREAD :
				return;
			case READY_STATE_COMPLETION :
			default:
				// 通信をキャンセル
				// 2010/10/04 abort()処理を解除
//				xmlhttpRequest.abort();
				xmlhttpRequest = null;
				break;
		}
	}
	try
	{
		try
		{
			// 定期送信が設定されている場合はキャンセル
			if ( tramsmissionId != null )
			{
				clearTimeout( tramsmissionId );
				tramsmissionId = null;
			}
		}
		catch(e){}
		document.all("__EVENTTARGET").value = args[0];
		document.all("__EVENTARGS").value = eventArgs;
		asyncFocusIs = "";
		asyncBallon = "";

		// レスポンス取得イベント処理
		xmlhttpRequest.onreadystatechange = xmlhttpResponseHandler;
		xmlhttpRequest.open( "POST", document.forms[0].action, false );
		xmlhttpRequest.setRequestHeader( "Content-Type", "application/x-www-form-urlencoded" );

		// パラメータ構築
		var param = createParameter();
		if ( param != null && param != "" )
		{
			if ( VISIBLE_PROGRESS )
			{
				// プログレスバー表示
				if ( progressBar == null )
				{
					progressBar = new ProgressBar();
				}
				progressBar.start();
			}
			else
			{
				progressBar = null;
			}
			clearExistingBdMsg();
			iFiled = new IField();
			iFiled.generate();
//			timeOutId = setTimeout( "communicationTimeOut()", ( 5 * 60 * 1000 ) );
			// 送信
			xmlhttpRequest.send( param );
			// フラグ初期化
			enterKeyEvent = true;
		}
		else
		{
			try
			{
				// 2010/10/04 abort()処理を解除
//				xmlhttpRequest.abort();
				xmlhttpRequest = null;
			}
			catch(e)
			{
			}
			clearSendFlag();

		}
	}
	catch( e )
	{
		followin();
		alert( "ajaxPostBack : " + e.message );
	}
}

/**
 * XMLHttpオブジェクトのステータスを受けるコールバック関数
 */
function xmlhttpResponseHandler()
{
	try
	{
		// ステータス読み込み終了
		switch( xmlhttpRequest.readyState )
		{
			case READY_STATE_HASREAD :

				isChangeBackColor = true;

				if ( progressBar != null )
				{
					progressBar.passage();
				}
				break;
			// ステータス送信完了
			case READY_STATE_COMPLETION :
				// 正常終了である場合
				if ( xmlhttpRequest.status == RESPONSE_OK )
				{
					var text = xmlhttpRequest.responseText;
					if (text.isJSON())
					{
						var myJsonObject = text.evalJSON();
						parseJSON(myJsonObject);
						// 2011/11/15 v5.2.2.6 処理が正常に成功した場合フラグを立てる。このフラグがたっている場合はvisibleSelect()を呼ばない
						if ( progressBar != null )
						{
							progressBar.setSuccess();
						}
						// 2011/11/15 v5.2.2.6 
					}
					else
					{
						createErrorWindow(text);
					}
				}
				// サーバ側でパラメターがロストした場合
				else if( xmlhttpRequest.status == 406 )
				{
				}
				// 異常終了である場合
				else
				{
					var text = xmlhttpRequest.responseText;
					createErrorWindow( text );
				}
				if ( progressBar != null )
				{
					progressBar.wait();
				}
				try
				{
					// 2010/10/04 abort()処理を解除
//					xmlhttpRequest.abort();
					xmlhttpRequest = null;
				}
				catch(e){}
				followinTimer();
				break;
		}
	}
	catch( e )
	{
		// プログレスバー終了
		if ( progressBar != null )
		{
			progressBar.end();
		}
		followin();
		alert( "xmlhttpResponseHandler : " + e.message );
	}
}
/**
 * 送信後の後処理
 */
function followin()
{
	// Add K.Fukumori 2006/11/08
	// Unlock the Enterkey focus move.
	canEnterkeyFocusMove = true;
	// Add END
	// プログレスバー終了
	if ( progressBar != null )
	{
		progressBar.end();
	}
	// 送信フラグを落とします。
	clearSendFlag();

	isFocusSelect = true;

	if ( asyncFocusIs != null && asyncFocusIs != "" )
	{
		var elem = document.all( asyncFocusIs );
		if ( elem != null )
		{
			// Add v4.2.3 2007.09.20
			if( elem.disabled==false )
			{
			// Add End
				// Add v4.2.5 2007.11.19
				try
				{
					// Start.2011/02/02 T.Sumiyama
					//Focusが既に当たっている時は関数だけを呼ぶ処理に変更
					if(document.activeElement == elem)
					{
						var eleFocusFunc = elem.onfocus;
						eval("setTimeout(" + eleFocusFunc + ",1);");
					}
					else
					{
						elem.focus();
					}
					// End.
				}
				catch(e)
				{
				}
				// Add End
				if ( document.activeElement.id == null ||
					 document.activeElement.id == "" )
				{
					setFocus( asyncFocusIs );
				}
			// Add v4.2.3 2007.09.20
			}
			// Add End
		}
	}
	if ( asyncBallon != null && asyncBallon != "" )
	{
		try
		{
			setTimeout( asyncBallon , FOCUS_OFFSET_TIME );

		}
		catch(e)
		{
			eval( asyncBallon );
		}
	}
	asyncFocusIs = "";
	asyncBallon = "";
	if ( iFiled != null )
	{
		iFiled.destroy();
	}
	// フラグ初期化
	enterKeyEvent = false;

}

/**
 * 時間差の送信後の後処理
 */
function followinTimer()
{
	try
	{
		setTimeout("followin()",1 );
	}
	catch(e)
	{
		followin();
	}
}

/**
 * 非同期通信用エラーウィンドウを作成します。
 *
 * @param text エラーウィンドウに出力するHTML文字列
 */
function createErrorWindow( text )
{
	try
	{
		if ( text != null && text != "" )
		{
			//Mod 2007/12/06 v4.2.5
			var hwind = window.open( "", "AsyncErrorWindow", "directories=no,status=no,toolbar=no" );
			// 2010/11/08 フォーカス対応
			if (!hwind.closed)
			{
				hwind.close();
				// Start. 2011/03/23 closeを確認してからopenするよう対応
				while(true)
				{
					if(hwind.closed)
					{
						hwind = window.open( "", "AsyncErrorWindow", "directories=no,status=no,toolbar=no" );
						break;
					}
				}
				// End. 2011/03/23
			}
			//End 2007/12/06 v4.2.5
			hwind.document.open();
			hwind.document.write( text );
			hwind.document.close();
			// Mod Start. 2010/10/12
			hwind.focus();
			// Mod End.
		}
		else
		{
			alert( "Connection Error." );
		}
	}
	catch(e){}
}
/**
 * 送信データの構築
 * @return 構築された送信データ
 */
//Mod 2007/10/03 v4.3
function createParameter()
{
	var frm = document.forms[0];
	var postdata = "";
	var eleLength = frm.elements.length;
	var elements = frm.elements;
	for (var i = 0 ; i < eleLength ; i++)
	{
		var element = elements[i];
		var eleTagName = element.tagName;
		var eleType = element.type;
		var eleName = element.name;
		var eleChecked = element.checked;
		if ( !element.disabled && eleName != "" &&
			 (
			   (
			     ( eleTagName=='INPUT' && eleType=='text' ) ||
			     ( eleTagName=='INPUT' && eleType=='password' ) ||
			     ( eleTagName=='INPUT' && eleType=='hidden' ) ||
			     ( eleTagName=='INPUT' && eleType=='radio' ) ||
			     ( eleTagName=='INPUT' && eleType=='checkbox' )
			   )
			   ||( eleTagName=='SELECT' )
			   ||( eleTagName=='TEXTAREA' )
			 )
		   )
		{
			if ( eleType=='checkbox' && !eleChecked )
			{
				continue;
			}
			if ( eleType=='radio' && !eleChecked )
			{
				continue;
			}
			if ( eleTagName=='SELECT' && element.multiple )
			{
				var optionsLength = element.options.length;
				for ( var j = 0; j < optionsLength; j++)
				{
					var option = element.options[j];

					if ( option.selected )
					{
						var data = encodeURI( option.value );
						data = encode( data );

						postdata += "&" + eleName + "=" + data;
					}
				}
			}
			var data = encodeURI( element.value );
			if ( element.name != "__VIEWSTATE" )
			{
				data = encode( data );
			}

			postdata += "&" + eleName + "=" + data;
		}
	}
	postdata += "&" + httpSyncMode + "=" + httpSyncModeAsync;
	postdata = postdata.substring(1);
	return postdata;
}
//End 2007/10/03 v4.3


//Add Start. T.Sumiyama
function parseJSON(myJsonObject)
{
	if (myJsonObject.responsexmlcontrol != null)
	{
		for ( var i = 0; i < myJsonObject.responsexmlcontrol.length; i++)
		{
			var elem = myJsonObject.responsexmlcontrol[i].control;
			if (elem != null)
			{
				var target = elem.id;
				var type = elem.type;

				if (document.all(target) == null)
				{
					continue;
				}
				// 変更した属性
				var attrs = elem.attrs;
				if (attrs != null)
				{
					for ( var j = 0; j < attrs.length; j++)
					{
						var name = attrs[j].attr.name;
						var value = attrs[j].attr.value;

						var targetElem = document.all(target);

						// チェックボックスとラジオボタンの時は、チェック部分とラベル部分に分けます
						if (type == "CheckBoxImpl" || type == "RadioButtonImpl")
						{
							var targetINPUT = null;
							var targetLABEL = null;
							if (name == "visible")
							{
								// 全体にかかる属性の場合はここでsetProperty
								setProperty(targetElem, name, value);
								continue;
							}
							var targetElems = targetElem.childNodes;
							for (var k = 0; k < targetElems.length; k++)
							{
								if(targetElems[k].tagName == "INPUT")
								{
									targetINPUT = targetElems[k];
								}
								else if (targetElems[k].tagName == "LABEL")
								{
									targetLABEL = targetElems[k];
								}
							}
							if ((name == "label-cssClass" || name == "innerText") && targetLABEL != null)
							{
								// ラベル部分にかかる属性の場合はここでsetProperty
								setProperty(targetLABEL, name,value);
								continue;
							}
							if (targetINPUT != null)
							{
								// コントロール部分にかかる属性の場合はここでsetProperty
								setProperty(targetINPUT, name, value);
							}
						}
						else if (type == "PullDownImpl" && type == "LinkedPullDownImpl")
						{
							setProperty(targetElem, name, value.toString());
						}
						// DateTextBox, PagerでouterHTMLが送られた場合はすべてを内包するspanタグのelementを渡します
						else if ((type == "DateTextBoxImpl" || type == "PagerImpl") && (name == "outerHTML" || name == "visible"))
						{
							var targetElem = targetElem.parentNode;
							setProperty(targetElem, name, value.toString());
						}
						// 2010/10/04 LinkedPullDownImpl追加
						// 2010/10/29 ListBoxImpl追加
						else if (targetElem.length != null && (type != "PullDownImpl" && type != "LinkedPullDownImpl" && type != "ListBoxImpl"))
						{
							for ( var m = 0; m < targetElem.length; m++)
							{
								setProperty(targetElem[m], name,value);
							}
						}
						else
						{
							setProperty(targetElem, name, value.toString());
						}
					}
				}
				var funcs = elem.funcs;
				for ( var k = 0; k < funcs.length; k++)
				{
					// 書き換え関数の取得
					var target = funcs[k].func.target;
					// 関数書き換え
					eval(target + "= new Function( funcs[k].func.value )");
				}
				var executes = elem.executes;
				if (executes.length > 0)
				{
					for ( var k = 0; k < executes.length; k++)
					{
						// 実行関数の取得
						var target = executes[k].exec.func;
						// 関数の実行
						try
						{
							// 即時行うスクリプトに関しては、実行します
							eval(target);
						}
						catch(e){}
						finally
						{
							// 変数を書き換える等のスクリプトに関しては、エラー処理後行います
							eval("\"" + target +"\"");
						}
					}
				}
			}
		}
	}

	if (myJsonObject.page != null)
	{
		// 読み込んだ順に実行します
		var page = myJsonObject.page;

		if (page.onloadAlert != null)
		{
			onloadAlert(page.onloadAlert.msg);
		}
		if (page.onloadscripts != null)
		{
			for ( var k = 0; k < page.onloadscripts.length; k++)
			{
				eval(page.onloadscripts[k].onloadscript);
			}
		}
		if (page.dispConfirm != null)
		{
			dispConfirm(
				page.dispConfirm.msg,
				page.dispConfirm.flag,
				page.dispConfirm.async);
		}
		if (page.focus != null)
		{
			asyncFocusIs = page.focus.id;
		}
		if (page.enterKeyFocus != null)
		{
			replaceEnterOders(page.enterKeyFocus.value);
		}
		if (page.resizeTo != null)
		{
			execResizeToCenter(
				page.dispConfirm.width,
				page.dispConfirm.height,
				page.dispConfirm.moveTo);
		}
		if (page.regulartransmission != null)
		{
			exceuteTramsmission(page.regulartransmission.time);
		}
		if (page.viewStateMap != null)
		{
			document.all(page.viewStateMap.id).value = page.viewStateMap.value;
		}
		if (page.ballonmessage != null)
		{
			asyncBallon = page.ballonmessage.msg;
		}

	}
}

/**
 * 文字列の置換処理
 *
 * @param text 対象文字列
 * @param sText 置換文字列
 * @param rText 置換後の文字列
 * @return 置換後の文字列
 *
 */
function replace( text, sText, rText )
{
	var dummy1 = "";
	var dummy2 = text;
	// 無限ループ
	while ( true )
	{
		// 検索
		index = dummy2.indexOf( sText, 0 );
		if ( index == -1 )
		{
			// 検索文字列がなければループを抜ける
			break;
		}
		// 置換
		text = dummy2.replace( sText, rText );
		dummy1 += text.substring( 0, index + rText.length );
		dummy2 =  text.substring( index + rText.length, text.length );
	}
	// 置換後の文字列を返却
	return dummy1 + dummy2;
}

/**
 * 文字列のエンコード
 *
 * @param text 対象文字列
 * @return 置換後の文字列
 *
 */
function encode( text )
{
	if ( text == null || text == "" )
	{
		return "";
	}
	var str  = "";
	var ch = "";
	for ( var i = 0; i < text.length; i++ )
	{
		ch = text.charAt(i);
		switch ( ch )
		{
			case "+" :
				str = str.concat( PLUS );
				break;
			case "&" :
				str = str.concat( AMPERSAND );
				break;
			default :
				str = str.concat( ch );
				break;
		}
	}
	// 置換後の文字列を返却
	return str;
}

/**
 * エンターオーダー順の書き換え
 *
 * @param orders エンターオーダー順 カンマ区切り
 */
function replaceEnterOders( orders )
{
	enterOrders = new Array();
	if ( orders != null && orders != "" )
	{
		enterOrders = orders.split(",");
		if ( enterOrders.length > 0 )
		{
			currentFocusElementId = enterOrders[ enterOrders.length-1 ];
		}
	}
}

/**
 * アラート表示
 *
 * @param msg 表示するメッセージ
 */
function onloadAlert( msg )
{
	alert( msg );
}

/**
 * フォーカスをセットする
 *
 * @param id フォーカスをセットするコントロールのid
 */
function setFocus( id )
{
	try
	{
		setTimeout("setFoucusTimer('"+ id +"');", FOCUS_OFFSET_TIME );
	}
	catch(e){}
}

/**
 * ウィンドウの中央表示処理
 *
 * @param width 幅
 * @param height 高さ
 * @param moveTo true 中央表示処理を行う false 中央表示処理を行わない
 *
 */
function execResizeToCenter( width, height, moveTo )
{
	try
	{
		if ( window.opener != null && !window.opener.closed )
		{
			var parseWidth = parseInt( width );
			var parseHeight = parseInt( height );
			if ( parseWidth > screen.availWidth )
			{
				parseWidth = screen.availWidth;
			}
			if ( parseHeight > screen.availHeight )
			{
				parseHeight = screen.availHeight;
			}
			if ( parseWidth > 0 && parseHeight > 0 )
			{
				this.resizeTo( width, height );
			}
			//if ( moveTo || moveTo == "true" )
			if ( (typeof moveTo == "boolean" && moveTo) || moveTo == "true" )
			{
				moveToCenter( this );
			}
		}
	}
	catch(e){}
}

/**
 * 定期送信の定義
 *
 * @param time 送信時間(秒)
 */
function exceuteTramsmission( time )
{
	try
	{
		var nTime = parseInt( time );

		if ( nTime <= 0 )
		{
			return;
		}
		tramsmissionId = setTimeout( "prepareTramsmission()", ( nTime * 1000 ) );
	}
	catch(e){}
}

/**
 * 定期送信の実行
 */
function prepareTramsmission()
{
	// Mod Start. 2010/10/12 子ウィンドウが存在する場合、定期送信を行わない
	try
	{
		if (dialogWindow == null || dialogWindow.closed || dialogWindows == null  ||  dialogWindows.length <= 0 )
		{
			// 2012/04/10 5.2.2.10 PDFヘルプのオープンと定期送信が重なってしまった場合に定期送信がストップしてしまう不具合対応(２重送信チェックに引っかかった場合はfalseで判定する)
			var ret = ajaxPostBack( 'page', 'clientPull' );
			if (ret === false) {
				exceuteTramsmission(regularTime);
			}
		}
		else
		{
			exceuteTramsmission(regularTime);
		}
	}
	catch(e)
	{
		exceuteTramsmission(regularTime);
	}
	// Mod End.
}

/**
 * 非同期通信のタイムアウト時によびだされます。
 */
function communicationTimeOut()
{
	alert( communicationTimeOutMessage );
	try
	{
		if ( xmlhttpRequest != null )
		{
			// 2010/10/04 abort()処理を解除
//			xmlhttpRequest.abort();
			xmlhttpRequest = null;
		}
	}
	catch(e){}
	try
	{
		if ( timeOutId != null )
		{
			clearTimeout( timeOutId );
			timeOutId = null;
		}
	}
	catch(e){}
}

/**
 * ユーザの入力を受け付けないフィールドを発生します。
 */
function IField()
{
	// 関数のプロトタイプ宣言
	this.generate = generate;
	this.destroy  = destroy;

	// プライベート変数
	var ID         = "id";
	var IFiled_TAG = "div";
	var IFiled_ID  = "IFiled";

	// フィールドの発生
	function generate()
	{
		destroy();
		var lockDiv = document.createElement( IFiled_TAG );
		lockDiv.setAttribute( ID, IFiled_ID );
		var prgStyle = lockDiv.style;
		prgStyle.position = 'absolute';
		prgStyle.backgroundColor = "blue";
//Satrt Mod T.Sumiyama for v5.2 2010/03/15
		prgStyle.filter ="alpha(style=1, opacity=0, finishopacity=0, startx=0, starty=0, finishx=99%, finishy=99%)";
		prgStyle.overflow = "hidden";
//End   Mod T.Sumiyama for v5.2 2010/03/15
		document.body.appendChild( lockDiv );
		var scrollLeft = parseInt( document.body.scrollLeft );
		var scrollTop  = parseInt( document.body.scrollTop );
		document.all( IFiled_ID ).style.top  = 0;
		document.all( IFiled_ID ).style.left = 0;
		document.all( IFiled_ID ).style.width = document.body.clientWidth + scrollLeft;
		document.all( IFiled_ID ).style.height = document.body.clientHeight + scrollTop;
		document.all( IFiled_ID ).style.filter = "alpha(style=1, opacity=0 )";
	}

	// フィールドの破棄
	function destroy()
	{
		if( document.all( IFiled_ID ) != null )
		{
			document.body.removeChild( document.all( IFiled_ID ) );
		}
	}
	return this;
}

function setProperty(targetElem, property, value)
{
	if ( targetElem.tagName=='SELECT' &&
			targetElem.style.visibility != "hidden" )
	{
		var pgorgressSelects = deActiveSelects[ "___xmlHttpRequestProgressBar" ];

		if ( pgorgressSelects != null && pgorgressSelects.length > 0 )
		{
			for ( var sl = 0; sl < pgorgressSelects.length; sl++ )
			{
				if ( targetElem.id == pgorgressSelects[sl] )
				{
					targetElem.style.visibility = "hidden";
				}
			}
		}
	}

	if (targetElem.tagName =="OPTION")
	{
		// 子要素の場合は、親要素を参照します
		targetElem = targetElem.parentNode;
	}

	if (property == "visible")
	{
		if (value == "true")
		{
			eval("targetElem.style.visibility = 'visible'");
		}
		else
		{
			eval("targetElem.style.visibility = 'hidden'");
		}
		return;
	}
	if (property == "disabled" || property == "checked" || property == "readOnly")
	{
		eval("targetElem." + property + "=" + value + "");
		return;
	}
	if (property == "cssClass" || property == "label-cssClass")
	{
		eval("targetElem.className=\"" + value + "\"");
		return;
	}
	if (property == "outerHTML")
	{
		targetElem.outerHTML = value;
		return;
	}
	if (property == "innerText" )
	{
		value.split("&nbsp;").join(" ");
		targetElem.innerText = value;
		return;
	}
	eval("targetElem." + property + "=\"" + value + "\"");
}

/**
 * IE9でeval関数を使用するとメモリリークする不具合があります。
 * http://support.microsoft.com/kb/2572253
 *
 * 一回のevalでリークする量はわずかだが、
 * 定期送信画面のように定期的にポーリングする画面が
 * ずっと開かれていた場合はかなりのメモリ消費になる。
 * 
 * リークしているメモリは画面を更新することで開放されるため
 * 同一画面で指定回数以上、定期送信を行った場合に
 * 強制的に一度画面を更新する。
 */
function clear_ie9_eval_memory_leak_2572253(intervalMsec) {
	var isIE9 = window.navigator.userAgent.indexOf("Trident/5.0") != -1;
	if (isIE9) {
		setTimeout(function(){
			if (!canSend("page", "clearMemoryLeakIE9")) {
				setTimeout(function(){
					clear_ie9_eval_memory_leak_2572253(intervalMsec);
				}, intervalMsec);
				return;
			}
			clearSendFlag();
			postBack("page", "clearMemoryLeakIE9");
		}, intervalMsec)
	}
}