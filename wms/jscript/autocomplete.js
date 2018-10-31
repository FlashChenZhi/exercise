
var SPAN_SELECT_BGCOLOR  = "#CEF7EF";
var SPAN_DEFAULT_BGCOLOR = "white";
var PARAM_DELEM = "__PARAM_DELEM__";
var ITEM_DELEM  = "__ITEM_DELEM__";
var X = "X";
var Y = "Y";
var LEFTTOP     = "LEFTTOP";
var LEFTBUTTOM  = "LEFTBUTTOM";
var RIGHTTOP    = "RIGHTTOP";
var RIGHTBUTTOM = "RIGHTBUTTOM";

var AUTOCOMPLETEDIV = "__AUTO_COMPLETE_";
var autoComolitePosition = new Array();
var deActiveSelects = new Array();
var activeTimer = null;
/** <jp>
 * オートコンプリートメソッド
 *
 * @param id オートコンプリートイベント発生クラスのId
 * @param target オートコンプリート対象のオブジェクト
 * @param comp オートコンプリートデータ表示クラス
 *
 </jp>*/
function autoCompleteEvent( id, target, comp )
{
	var element = document.all( id );
	var complete = document.all( comp );

	switch( event.keyCode )
	{
		case Event.KEY_ESC:
			delList( id + AUTOCOMPLETEDIV );
			element.focus();
			return;
		case Event.KEY_UP:
			changeList( id + AUTOCOMPLETEDIV, false );
			return;
		case Event.KEY_DOWN:
			changeList( id + AUTOCOMPLETEDIV, true );
			return;
		case Event.KEY_TAB:
			isFocusSelect = true;
			clearKeyCode();
			element.onfocus();
			return;
	}
	delList( id + AUTOCOMPLETEDIV );
	ajaxPostBack( id, target );
}

/** <jp>
 * オートコンプリートで有効なキーコードの確認メソッド
 *
 * @param keyCode キーコード
 * @return true 有効キー false 無効キー
 </jp>*/
function isEffective( keyCode )
{
	switch( event.keyCode )
	{
		case Event.KEY_RETURN:
		case Event.KEY_LEFT:
		case Event.KEY_RIGHT:
		case Event.KEY_SHIFT:
		case Event.KEY_CTRL:
		case Event.KEY_ALT:
		case 29:  // No Change
		case 33:  // Home
		case 35:  // PageUp
		case 36:  // PageDown
		case 45:  // Insert
		case 91:  // Windows Key
		case 93:  // RightClick Key
		case 112: // F1
		case 113: // F2
		case 114: // F3
		case 115: // F4
		case 116: // F5
		case 117: // F6
		case 118: // F7
		case 119: // F8
		case 120: // F9
		case 121: // F10
		case 122: // F11
		case 123: // F12
		case 145: // ScrLK
		case 191: // Back Slash
		case 242: // Change
		case 243: // Change
		case 244: // Change
			return false;
	}
	return true;
}

/** <jp>
 * オートコンプリートデータセット
 *
 * @param target オートコンプリート対象のオブジェクト
 * @param data オートコンプリートデータ
 *
 </jp>*/
function setAutoComplete( target, data )
{
	// <jp>オートコンプリートもとのtextのIdを作成</jp>
	var textId = replace( target, AUTOCOMPLETEDIV, "" );

	// <jp>オートコンプリートもとのtextを作成</jp>
	var element = document.all( textId );

	// <jp>オートコンプリート用のSPAN取得</jp>
	var complete = document.all( target );

	// <jp>オートコンプリート用のSPANが存在しない場合は処理を行わない。</jp>
	if ( complete == null )
	{
		return;
	}

	// <jp>オートコンプリート候補が存在しない場合</jp>
	if ( data.length == 0 )
	{
		// <jp>リストの削除</jp>
		delList( target );
		return;
	}

	complete.width = element.offsetWidth;

	// <jp>テキスト初期化</jp>
	complete.innerHTML = "";

	// <jp>オートコンプリート用のSPANが非表示である場合</jp>
	if ( complete.style.visibility == "hidden" )
	{
		// <jp>アクティブ化</jp>
		var offsets = Position.cumulativeOffset(element);
		complete.style.left = offsets[0] + 'px';
		complete.style.top  = (offsets[1] + element.offsetHeight) + 'px';
		complete.style.width = element.offsetWidth + 'px';

		// <jp>コンプリートリストのセット</jp>
		complete.innerHTML = createList( target, data, textId );

		// <jp>Selectの非表示化</jp>
		hiddenSelect( complete );

		complete.style.visibility = "visible";
		new Effect.Appear(complete,{duration:0.3});
	}

	// <jp>値が存在しない場合</jp>
	if ( complete.innerHTML.length == 0 )
	{
		// <jp>リストの削除</jp>
		delList( target );
	}

	// <jp>自動削除処理</jp>
	try
	{
		activeTimer = setTimeout( "delList('" + target + "')", 30 * 1000 );
	}
	catch(e)
	{
	}
}

/** <jp>
 * オートコンプリートリストの作成
 *
 * @param target オートコンプリート対象のオブジェクト
 * @param data [0] アイテムコード [1] アイテム名 [2] アイテムの値
 * @param textId オートコンプリートイベント発生クラスのId
 * @return 作成されたオートコンプリートリスト
 *
 </jp>*/
function createList( target, data, textId )
{
	var result = new Array();
	var ret = "";
	var temp = data.split( ITEM_DELEM );

	for ( var i = 0; i < temp.length - 1; i++ )
	{
		var val = temp[i].split( PARAM_DELEM );
		var tag = "<tr id='" + target + i + "' onclick=\"setData('" + textId + "','"+ val[2] +"');delList('"+ target +"');\" onmouseover=\"changeOverList('" + target + "','"+ i +"');\" ";
			tag+= "onkeydown=\" if( event.keyCode == Event.KEY_ESC ) { delList('"+ target +"'); }\" width='100%'>";
			tag+= "<td width='100%' align='left' style='font-size:12px; font-family:monospace; margin-right: 0px; padding-right: 0px; border-right: 0px;' nowarp>" + val[0] + "</td>";
			tag+= "<td width='100%' align='center' style='font-size:12px; font-family:monospace; margin-right: 0px; padding-right: 0px; border-right: 0px;' nowarp>&nbsp;&nbsp;</td>";
			tag+= "<td width='100%' align='right' style='color:#006400; font-size:12px; font-family:monospace; margin-left: 0px; padding-left: 0px; border-left: 0px' nowarp>" + val[1] + "</td>";
			tag+= "</tr>";
		result.push( tag );
	}
	for ( var i = 0; i < result.length; i++ )
	{
		ret += result[i];
	}

	if ( result.length > 0 )
	{
		ret = "<table border='0' cellpadding='0' cellspacing='0' bgcolor='" + SPAN_DEFAULT_BGCOLOR + "'>" + ret + "</table>";
	}

	// <jp>オートコンプリートの件数と表示位置の指定 現在位置, 件数をセット</jp>
	autoComolitePosition[ target ] = new Array( -1, temp.length - 1 );

	return ret;
}

/** <jp>
 * サーバーへの送信
 *
 * @param textId オートコンプリート対象のオブジェクト
 * @param data 送信データ
 *
 </jp> */
function setData( textId, data )
{
	// <jp>ユーザビリティアップの為、連打チェックを破棄しユーザ操作による連続送信を許容する。</jp>
	clearSendFlag();
	ajaxPostBack( textId, "autoCompleteItemClick", data );
}

/** <jp>
 * リストの削除
 *
 * @param target リストのId
 </jp> */
function delList( target )
{
	var complete = document.all( target );

	if ( complete != null )
	{
		complete.style.visibility = "hidden";
	}

	visibleSelect( complete );

	try
	{
		if ( activeTimer != null )
		{
			clearTimeout( activeTimer );
		}
	}
	catch(e)
	{
	}
}

/** <jp>
 * リストの表示状態確認
 *
 * @param target リストのId
 * @return true 表示 false 非表示
 </jp> */
function isActiveList( target )
{
	if (document.all( target ))
		document.all( target ).style.visibility != "hidden";
//	return document.all( target ).style.visibility != "hidden";
}

/** <jp>
 * リストの選択候補の変更
 *
 * @param target リストのId
 * @param next true 次へ false 前へ
 </jp> */
function changeList( target, next )
{
	// <jp>リストが存在してる場合</jp>
	if ( isActiveList( target ) )
	{
		var array = autoComolitePosition[ target ];

		// <jp>現時位置</jp>
		var now   = parseInt( array[0] );
			now   = ( now <= 0 ) ? 0 : now ;
		var point = parseInt( array[0] );

		// <jp>最大件数</jp>
		var max   = parseInt( array[1] );

		// <jp>下へ行く場合</jp>
		if ( next )
		{
			point++;
		}
		else
		{
			point--;
		}

		if ( point <= 0 )
		{
			point = 0;
		}
		else if ( point >= max )
		{
			point = max -1;
		}
		followChangeList( target, point, max );
	}
}

/** <jp>
 * ハイライト表示化
 *
 * @param target リストのId
 * @param point ハイライト表示位置
 </jp> */
function changeOverList( target, point )
{
	// <jp>リストが存在してる場合</jp>
	if ( isActiveList( target ) )
	{
		var array = autoComolitePosition[ target ];
		// <jp>最大件数</jp>
		var max = parseInt( array[1] );
		followChangeList( target, point, max );
	}
}

/** <jp>
 * 非ハイライト表示化
 *
 * @param target リストのId
 * @param point 選択位置
 * @param max 最大件数
 </jp> */
function followChangeList( target, point, max )
{
	for ( var i = 0; i < max; i++ )
	{
		if ( i == point )
		{
			document.all( target + i ).style.backgroundColor = SPAN_SELECT_BGCOLOR;
		}
		else
		{
			document.all( target + i ).style.backgroundColor = SPAN_DEFAULT_BGCOLOR;
		}
	}
	autoComolitePosition[ target ] = new Array( point, max );
}

/** <jp>
 * リストの選択
 *
 * @param target リストのId
 * @return true アイテムクリックイベント発行 false イベント未発行
 </jp> */
function enterList( target )
{
	// <jp>リストが存在してる場合</jp>
	if ( isActiveList( target ) )
	{
		var array = autoComolitePosition[ target ];

		var now  = parseInt( array[0] );
		var elem = document.all( target + now );

		if ( elem != null )
		{
			elem.click();
			return true;
		}
		else
		{
			return false;
		}
	}
	return false;
}

/** <jp>
 * Selectの非表示化
 *
 * @param element リストのエレメント
 </jp> */
function hiddenSelect( element )
{
	var deActiveSelect = new Array();

	var elementCoordinates = createCoordinates( element );

	var selects = document.all.tags("SELECT");
	for ( var i = 0; i < selects.length; i++)
	{
		var obj = selects[i];

		if ( obj.style.visibility == "hidden" )
		{
			if ( !existsSelect( obj.id ) )
			{
				continue;
			}
		}
		var selectCoordinates = createCoordinates( obj );

		// <jp>重なりチェック</jp>
		if ( compareCoordinates( selectCoordinates, elementCoordinates ) ||
			 compareCoordinates( elementCoordinates, selectCoordinates ) )
		{
			obj.style.visibility = "hidden";
			deActiveSelect[deActiveSelect.length] = obj.id;
		}
	}
	deActiveSelects[ element.id ] = deActiveSelect;
}

/** <jp>
 * elementの座標情報取得
 *
 * @param elemnet 座標情報を取得したいelement
 * @return elementの座標情報
 </jp> */
function createCoordinates( element )
{
	var offsets = Position.cumulativeOffset( element );

	var elementLeftTop = new Array();
	elementLeftTop[X] = offsets[0];
	elementLeftTop[Y] = offsets[1];

	var elementLeftButtom = new Array();
	elementLeftButtom[X] = offsets[0];
	elementLeftButtom[Y] = offsets[1] + element.offsetHeight;

	var elementRightTop = new Array();
	elementRightTop[X] = offsets[0] + element.offsetWidth;
	elementRightTop[Y] = offsets[1];

	var elementRightButtom = new Array();
	elementRightButtom[X] = offsets[0] + element.offsetWidth;
	elementRightButtom[Y] = offsets[1] + element.offsetHeight;

	var coordinates = new Array();
	coordinates[LEFTTOP] = elementLeftTop;
	coordinates[LEFTBUTTOM] = elementLeftButtom;
	coordinates[RIGHTTOP] = elementRightTop;
	coordinates[RIGHTBUTTOM] = elementRightButtom;

	return coordinates;
}

/** <jp>
 * 座標の重なりをチェックします
 *
 * @param target 比較元element
 * @param src 比較先element
 * @return true 座標が重なっている false 座標が重なっていない
 </jp> */
function compareCoordinates( target, src )
{
	//    src
	//   +------+  target
	//   |  +---+-----+
	//   |  |   |     |
	//   +--+---+     |
	//      |         |
	//      +---------+
	// target[LEFTTOP]
	if( src[LEFTTOP][X] <= target[LEFTTOP][X] &&  target[LEFTTOP][X] <= src[RIGHTBUTTOM][X] &&
	    src[LEFTTOP][Y] <= target[LEFTTOP][Y] &&  target[LEFTTOP][Y] <= src[RIGHTBUTTOM][Y] )
	{
		return true;
	}

	//               target
	//      +------------+
	//      |            |
	// src  |            |
	//    +-+---+        |
	//    | +---+--------+
	//    |     |
	//    +-----+
	// target[LEFTBUTTOM]
	if( src[LEFTTOP][X] <= target[LEFTBUTTOM][X] &&  target[LEFTBUTTOM][X] <= src[RIGHTBUTTOM][X] &&
	    src[LEFTTOP][Y] <= target[LEFTBUTTOM][Y] &&  target[LEFTBUTTOM][Y] <= src[RIGHTBUTTOM][Y] )
	{
		return true;
	}

	//                  src
	//   target    +------+
	//    +--------+--+   |
	//    |        |  |   |
	//    |        +--+---+
	//    |           |
	//    +-----------+
	// target[RIGHTTOP]
	if( src[LEFTTOP][X] <= target[RIGHTTOP][X] &&  target[RIGHTTOP][X] <= src[RIGHTBUTTOM][X] &&
	    src[LEFTTOP][Y] <= target[RIGHTTOP][Y] &&  target[RIGHTTOP][Y] <= src[RIGHTBUTTOM][Y] )
	{
		return true;
	}

	// target
	//  +------------+
	//  |            |  src
	//  |         +--+---+
	//  |         |  |   |
	//  +---------+--+   |
	//            |      |
	//            +------+
	// target[RIGHTBUTTOM]
	if( src[LEFTTOP][X] <= target[RIGHTBUTTOM][X] &&  target[RIGHTBUTTOM][X] <= src[RIGHTBUTTOM][X] &&
	    src[LEFTTOP][Y] <= target[RIGHTBUTTOM][Y] &&  target[RIGHTBUTTOM][Y] <= src[RIGHTBUTTOM][Y] )
	{
		return true;
	}

	//      target
	//    +---------+
	//    |         |  src
	// +--+---------+--+
	// |  |         |  |
	// |  |         |  |
	// +--+---------+--+
	//    |         |
	//    |         |
	//    +---------+
	// over
	if( src[LEFTTOP][X] <= target[LEFTTOP][X] && target[RIGHTTOP][X] <= src[RIGHTTOP][X] &&
	    target[LEFTTOP][Y] <= src[LEFTTOP][Y] && src[LEFTBUTTOM][Y] <= target[LEFTBUTTOM][Y] )
	{
		return true;
	}

	return false;
}

/** <jp>
 * Selectの表示化
 *
 * @param element リストのエレメント
 </jp> */
function visibleSelect( element )
{
	if ( element == null )
	{
		return;
	}

	// v5.2.2.6 start
	var fromProgressBar = false;
	if (arguments.length == 2)
	{
		fromProgressBar = arguments[1];
	}
	// v5.2.2.6 end
	var deActiveSelect = deActiveSelects[ element.id ];
	deActiveSelects[ element.id ] = new Array();

	if ( deActiveSelect == null ||
		 deActiveSelect == "undefined" ||
		 deActiveSelect == undefined
		)
	{
		return;
	}

	for ( var i = 0; i < deActiveSelect.length; i++)
	{
		var targetSelId = deActiveSelect[i];

		if ( !existsSelect( targetSelId ) )
		{
			// v5.2.2.6 start
			if (fromProgressBar && targetSelId.match(/\$[\d]+\$[\d]+/))
			{
				// プログレスバー表示時で対象のSelectがリストセル内のオブジェクトの場合は無視する(リストセルは再描画されるのでvisibilityに戻す必要なし)
				//alert("continue");
				continue;
			}
			// v5.2.2.6 end
			//Mod 2007/10/02 v4.2.3
			if(document.getElementById(targetSelId)!=null)
			{
				document.all( targetSelId ).style.visibility = "visible";
			}
			//End 2007/10/02 v4.2.3
		}
	}
}

/** <jp>
 * idで指定されたelementがフレームワークによる非表示状態の
 * エレメント情報に存在しているか確認します。
 *
 * @param id 確認したいelementのid
 * @return true 存在している false 存在していない
 </jp> */
function existsSelect( id )
{
	for ( j in deActiveSelects )
	{
		var otherDeActiveSelect = deActiveSelects[j];

		for ( k in otherDeActiveSelect )
		{
			if ( id == otherDeActiveSelect[k] )
			{
				return true;
			}
		}
	}
	return false;
}
