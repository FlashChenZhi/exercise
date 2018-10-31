	// $Id: control.js 8078 2014-12-23 01:51:56Z kumano $
/**
<jp> * サーバコントロールのカスタムタグが使用するJavaScriptです。</jp>
 * @version $Revision: 8078 $
 * @author  $Author: kumano $
 */
/*--------------------------------------------------------------------------*/
/* グローバル変数                                                           */
/*--------------------------------------------------------------------------*/
var dialogWindow;
var menu_num = new Array();
var ptn = new RegExp('[ -~｡-ﾟ]');
var isFocusSelect = true;


/**
<jp> * [関数の説明]</jp>
<jp> * ダイアログから親画面へのパラメータ渡しに使用します。</jp>
<jp> * [使用するカスタムタグ名]</jp>
 * PageTag
<jp> * [引数の説明]</jp>
<jp> * array 親画面へ渡すパラメータの配列</jp>
 */
function setParameters(array)
{
}

/**
<jp> * [関数の説明]</jp>
<jp> * エンターキーのフォーカス移動で使用します。</jp>
<jp> * [使用するカスタムタグ名]</jp>
 * PageTag
<jp> * [引数の説明]</jp>
<jp> * obj フォーカス移動順番文字列</jp>
<jp> *     コントロールId名を区切り文字(カンマ)でつなげた文字列</jp>
 */
function focusEnterMove(obj)
{
}

/**
<jp> * [関数の説明]</jp>
<jp> * ファンクションキー対応で使用します。</jp>
<jp> * [使用するカスタムタグ名]</jp>
 * PageTag
<jp> * [引数の説明]</jp>
<jp> * functionObjects Id名、ファンクションキー、イベント名を格納したObjectの配列</jp>
<jp> *                 ファンクションキーの数だげ必要</jp>
<jp> * systemParams    ApplicationConfigに定義されているファンクションキーの動作の配列</jp>
<jp> *                 ファンクションキーの数だげ必要</jp>
 */
function functionKeyEvent(functionObjects, systemParams)
{
}

/**
<jp> * [関数の説明]</jp>
<jp> * ダイアログウィンドウ（dialogWindow）に対しクローズを行います。</jp>
<jp> * [使用するカスタムタグ名]</jp>
 * PageTag
<jp> * [引数の説明]</jp>
<jp> * なし</jp>
 */
function closeDialog()
{
}

/**
<jp> * [関数の説明]</jp>
<jp> * ウィンドウの前面表示を行います。</jp>
<jp> * [使用するカスタムタグ名]</jp>
 * PageTag
<jp> * [引数の説明]</jp>
<jp> * windowObject 前面表示を行うウィンドウObject</jp>
 */
function setFocusWindow(windowObject)
{
}

/**
<jp> * [関数の説明]</jp>
<jp> * ウィンドウ中央表示を行います。</jp>
<jp> * [使用するカスタムタグ名]</jp>
 * PageTag
<jp> * [引数の説明]</jp>
<jp> * windowObject 中央表示を行うウィンドウObject</jp>
 */
function moveToCenter(windowObject)
{
}

/** <jp>
 * [関数の説明]
 * 文字色を指定色に変更します。
 *   ※現状はTypeが"text"のときのみ文字色を変えています。
 *     もし仕様がかわりcheckbox、radioなども文字色を変える必要が出てきたときのために
 *     if文で分岐させていますが、今のところreturnを返しています。
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * itemname  文字色を切り替えるObject名またはObject
 * col       文字色に指定する色
 </jp> */
function changeColor(itemname, col)
{
	if( itemname == "" ) return;
	if( col == "" ) return;

	var obj;
	if (typeof itemname == 'object') {
		obj = itemname;
	} else {
//Start Mod T.Sumiyama for v5.2 2010/03/15
		obj = document.getElementById(itemname);
//		obj = document.all.item(itemname);
//End Mod T.Sumiyama for v5.2 2010/03/15
	}

	// textboxなどObjectが複数存在しない場合あるいはSELECTタグのとき
	if (!obj.length || obj.tagName == "SELECT") {
		if (obj.tagName == "INPUT") {
			if (obj.type == "button") {
				return;
			} else if (obj.type == "checkbox") {
				//checkboxが一つしか存在しない場合 背景色の指定は出来ません
				return;
			}
		} else if (obj.tagName == "SELECT") {
			return;
		}
	} else {
		//checkbox、radio、selectなどの同一名称で複数存在する場合、背景色の指定は出来ません
		return;
	}

	//obj.style.setAttribute("color", col);
	obj.style.color=col;
}


/** <jp>
 * [関数の説明]
 * 背景色を指定色に変更します。
 *   ※現状はTypeが"text"のときのみ背景色を変えています。
 *     もし仕様がかわりcheckbox、radioなども背景色を変える必要が出てきたときのために
 *     if文で分岐させていますが、今のところreturnを返しています。
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * itemname  背景色を切り替えるObject名またはObject
 * col       背景色に指定する色
 </jp> */
var isChangeBackColor = true;
function changeBackcolor(itemname, col)
{
	if ( !isChangeBackColor ) return;
	if( itemname == "" ) return;
	if( col == "" ) return;

	var obj;

	if (typeof itemname == 'object') {
		obj = itemname;
	} else {
//Start Mod T.Sumiyama for v5.2 2010/03/15
		obj = document.getElementById(itemname);
//		obj = document.all.item(itemname);
//End Mod T.Sumiyama for v5.2 2010/03/15
	}

	//textboxなどObjectが複数存在しない場合あるいはSELECTタグのとき
	if (!obj.length || obj.tagName == "SELECT") {
		if (obj.tagName == "INPUT") {
			if (obj.type == "button") {
				return;
			} else if (obj.type == "checkbox") {
				//checkboxが一つしか存在しない場合 背景色の指定は出来ません
				return;
			}
		} else if (obj.tagName == "SELECT") {
			return;
		}
	} else {
		//checkbox、radio、selectなどの同一名称で複数存在する場合、背景色の指定は出来ません
		return;
	}

	// Commented by Muni.. Support TF
	//obj.style.setAttribute("background", col);

	try
	{
		obj.style.background=col;
	}
	catch (e)
	{
	}
	// Done

	//テキストボックスのハイライト表示が残ったままになることの防止策です。
	var tmp = obj.value;
	obj.value = tmp;
}


/** <jp>
 * [関数の説明]
 * 指定されたObjectが配置してあるTD、TR、TABLE句の背景色を取得します。
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * id 背景色を取得するObjectのIDを指定します。
 * [戻り]
 * TD、TR、TABLE句の背景色を返します。
 </jp> */
function getBgColor(id)
{
	if( id == "" ) return null;

	var obj = document.all(id);

	if( !obj ) return;

	// v5.2.4.0
	if (READ_ONLY_BG_COLOR) {
		return READ_ONLY_BG_COLOR;
	}
	
	if( id.match(/\$[\d]+\$[\d]+/) )
	{
		return "#D4D0C8";
	}

	/*var tagarray = new Array("TD", "TR", "TBODY", "TABLE");

	for(i in tagarray) {
		var parent = obj.parentElement;
		if( parent.tagName == tagarray[i] ) {
			if(parent.bgColor != "") {
				return parent.bgColor;
			}
		}
		obj = parent;
	}
	return null;*/

// Mod Start. 2010/10/04 オブジェクトの背景色を取得する処理を修正
//	var tagarray = new Array("TD", "TR", "TBODY", "TABLE");
//
//	for(var i = 0; i < tagarray.length; i++) {
//		var parent = obj.parentElement;
//		if( parent.tagName == tagarray[i] ) {
//			if(parent.bgColor != "") {
//				return parent.bgColor;
//			}
//		}
//		obj = parent;
//	}
//	return null;
	var tagarray = new Array("TD", "TR", "TBODY", "TABLE");

	var parent;
	while(true)
	{
			 parent = obj.parentElement;

			 if (parent == null || parent == undefined)
			 		 break;

			 for(var i = 0; i < tagarray.length; i++) {
			 		 if( parent.tagName == tagarray[i] ) {
			 		 		 if(parent.bgColor != "")
			 		 		 		 return parent.bgColor;
			 		 		 else if (parent.style.backgroundColor != "")
			 		 			return parent.style.backgroundColor;
			 		 }
			 		 obj = parent;
			 }
	}
	return null;
// Mod End.
}

/** <jp>
 * [関数の説明]
 * 書式化されたデータから区切り文字を外します。
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * str       書式化されたデータ
 * splitChar 区切り文字
 * [戻り]
 * 区切り文字を外したデータを返します。
 </jp> */
function getString(str,splitChar)
{
	if( str == "" ) return str;
	if( splitChar == "" ) return str;

	while (str.indexOf(splitChar) > -1){
		str = str.replace( splitChar, "" );
	}
	return str;
}

/** <jp>
 * [関数の説明]
 * カンマ編集します。
 * データの少数点以下桁数が、指定された桁数より小さい場合に、
 * 小数点以下の桁数を調整します。
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * str データ
 * pos 少数部の桁数
 * [戻り]
 * カンマ編集されたデータを返します。
 </jp> */
// カンマフォーマット「.」、「,」の扱いを逆にする場合は、control_forCommaFormat.jsを使用する
function getComma(str,pos)
{
	var strlen = 0;
	var i = 0;
	var minus = false;

	if( str == "" ) return "";
	if( pos == "" ) pos = 0;

	if( str.indexOf("-") == 0 ) {
		minus = true;
		str = str.substring(1);
	}
	if( str.match(/[^0123456789.]/) ) return "";

	if( str.indexOf(".") >= 0 ) {
		if( str.indexOf( ".", (str.indexOf(".")+1)) >= 0 ) return "";
	}
	if( str.indexOf(".") == -1 ) {
		str = parseInt(str, 10).toString();
	}
	else
	{
		// v5.2.2.18 0.0000001が指数表現に変換されてしまう不具合の修正。parseFloatは使わずに正規表現を使用して適切な数値変換を行う
		//str = parseFloat(str, 10).toString();
		// 1以下の数値を判定する正規表現(00.1、0.1、.1を対象としている)
		var underOneRegExp = /^0+\.|^\./;
		// 1以下の数値の場合は0を1つだけつけ、それ以外(1以上の場合)は初めの0を削除(例：0123.123を123.123にする)
		str = underOneRegExp.test(str) ? str.replace(underOneRegExp, "0.") : str.replace(/^0+/, "");
	}
	strlen = str.indexOf(".");
	if( strlen < 0 ) strlen = str.length;
	reStr = "";

	for( i = strlen; i > 0; i-- ) {
		if( i != 0 & i % 3 == 0 & i < strlen ) {
			reStr += ",";
		}
		reStr += str.charAt(strlen - i);
	}

	reStr += str.substring( strlen, str.length );

	if( pos > 0 && pos > (str.length - (strlen + 1)) ) {
		i = 0;
		if( (str.length - strlen) > 0 ) {
			i = str.length - (strlen + 1);
		} else {
			reStr += ".";
		}
		for( ; i < pos; i++ ) {
			reStr += "0";
		}
	}

	if(minus) {
		reStr = "-" + reStr;
	}

	return reStr;
}

// add 2007/06/06 Nakao
/** <jp>
 * [関数の説明]
 * 小数点編集します。
 * データの少数点以下桁数が、指定された桁数より小さい場合に、
 * 小数点以下の桁数を調整します。
 * [引数の説明]
 * str データ
 * pos 少数部の桁数
 * [戻り]
 * 小数点編集されたデータを返します。
 </jp> */
// カンマフォーマット「.」、「,」の扱いを逆にする場合は、control_forCommaFormat.jsを使用する
function getDecimalPoint(str,pos)
{
	var strlen = 0;
	var i = 0;
	var minus = false;

	// strが空白ではないか確認、空白の場合空白をreturnする
	if( str == "" ) return "";

	// posが空白ではないか確認、空白の場合はposは0とする
	if( pos == "" ) pos = 0;

	// strの先頭が"-"ではないか確認、-の場合minusフラグをtrueにする
	// strの先頭から-を削除する
	if( str.indexOf("-") == 0 ) {
		minus = true;
		str = str.substring(1);
	}

	// strの内容が0123456789.のみかどうか確認これら以外が
	// 入っている場合、空白をreturnする
	if( str.match(/[^0123456789.]/) ) return "";

	// strに小数点.が２つ以上入力されていない確認、２つ以上
	// 入力されている場合、空白をreturnする
	if( str.indexOf(".") >= 0 ) {
		if( str.indexOf( ".", (str.indexOf(".")+1)) >= 0 ) return "";
	}

	// strに小数点が含まれているかをチェック
	if( str.indexOf(".") == -1 ) {
		str = parseInt(str, 10).toString();
	}
	else
	{
		str = parseFloat(str, 10).toString();
	}


	strlen = str.indexOf(".");
	if( strlen < 0 ) strlen = str.length;
	reStr = "";

	for( i = strlen; i > 0; i-- ) {
		if( i != 0 & i % 3 == 0 & i < strlen ) {
			reStr += "";
		}
		reStr += str.charAt(strlen - i);
	}

	reStr += str.substring( strlen, str.length );

	if( pos > 0 && pos > (str.length - (strlen + 1)) ) {
		i = 0;
		if( (str.length - strlen) > 0 ) {
			i = str.length - (strlen + 1);
		} else {
			reStr += ".";
		}
		for( ; i < pos; i++ ) {
			reStr += "0";
		}
	}

	if(minus) {
		reStr = "-" + reStr;
	}

	return reStr;
}
// add end 2007/06/06 Nakao

/** <jp>
 * [関数の説明]
 * 指定された書式に書式化します。
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * str    データ
 * format 書式
 * pause  区切り文字
 * [戻り]
 * 書式編集されたデータを返します。
 </jp> */
function getFormat(str,format,pause)
{
	if( str == "" ) return str;
	if( format == "" ) return str;

	var sidx = 0;
	var eidx = 0;
	var value_sidx = 0;
	var value_eidx = 0;
	var value = "";
	var pos = 0;

	while( format.indexOf( pause, sidx ) != -1 ) {
		eidx = format.indexOf( pause, sidx );
		value_eidx = eidx - pos;
		value = value + str.substring( value_sidx, value_eidx ) + pause;
		sidx = eidx + pause.length;
		value_sidx = value_eidx;
		pos += pause.length;
	}

	value = value + str.substring( value_sidx, str.length );

	return value;
}

/** <jp>
 * [関数の説明]
 * データが指定されたバイト数を超えていないかチェックします。
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * str  データ
 * bytes バイト数
 * [戻り]
 * 指定されたバイト数を超えていたら false、超えていなければtrueを返します。
 </jp> */
function isByteCheck(str,bytes)
{
	if( str == "" ) return true;
	if( bytes == "" ) return true;

	if( getByte( str ) > bytes ) {
		return false;
	} else {
		return true;
	}
}


/** <jp>
 * [関数の説明]
 * データの桁数が指定された桁数を超えていないかチェックします。
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * str  データ
 * numlen 整数部桁数
 * decimallen 少数部桁数
 * [戻り]
 * 指定されたバイト数を超えていたら false、超えていなければtrueを返します。
 </jp> */
// カンマフォーマット「.」、「,」の扱いを逆にする場合は、control_forCommaFormat.jsを使用する
function isNumberOfDigitCheck(str,numlen,decimallen)
{
	if( str == "" ) return true;

	if( numlen >= 0 ){
		if( str.indexOf(".") == -1 ) {
			if( !isByteCheck( str, numlen ) ) return false;
		} else {
			if( !isByteCheck( str.substring( 0, str.indexOf(".")), numlen ) ) return false;
		}
	}

	if( decimallen >= 0 ){
		if( str.indexOf(".") >= 0 ) {
			var idx = str.indexOf(".") + 1;
			if( !isByteCheck( str.substring( idx, str.length ), decimallen ) ) return false;
		}
	}

	return true;
}

/** <jp>
 * [関数の説明]
 * 数値の範囲チェックを行います。
 * [使用するカスタムタグ名]
 * NumberTextBoxTag
 * [引数の説明]
 * val  データ
 * min  最小値
 * max  最大値
 * [戻り]
 * 指定されてる範囲外の数値ならfalse、範囲内の数値ならtrueを返します。
 </jp> */
// カンマフォーマット「.」、「,」の扱いを逆にする場合は、control_forCommaFormat.jsを使用する
function isNumberCheckRange(val, min, max)
{
	var in_val = replaceAll(replaceAll(val, ',', ''), ' ', '');

	if (in_val == '')
	{
		return true;
	}

	if (isNaN(in_val))
	{
		return false;
	}

	if (!isNaN(max) && (max != ''))
	{
		var num_max = new Number(max);
		if (in_val > num_max)
		{
			return false;
		}
	}

	if (!isNaN(min) && (min != ''))
	{
		var num_min = new Number(min);
		if (in_val < num_min)
		{
			return false;
		}
	}
	return true;
}

/** <jp>
 * [関数の説明]
 * データのバイト数を取得します。
 * 全角文字は2byte、半角カナの場合は1byteとして計算します。
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * str データ
 * [戻り]
 * バイト数を返します。
 </jp> */
function getByte( str )
{
	str = RTrim( str );
	var i, cnt =0;
	var CLetter;

	for (i=0; i<str.length; i++) {

		CLetter = str.charAt(i);

		if (CLetter.match(ptn)) {
			cnt++;
    	}else{
			cnt += 2;
    	}
	}
	return cnt;
}

/** <jp>
 * [関数の説明]
 * 引数を右TRIMします。
 * TRIMされる文字列は半角スペースと全角スペースです。
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * str データ
 * [戻り]
 * 右TRIMされた文字列を返します。
 </jp> */
function RTrim(str)
{
	//Mod 2007/10/02 v4.2.3
    str = str.replace(/[ ]+$/,"");
	//End 2007/10/02 v4.2.3
    return str;
}

/** <jp>
 * [関数の説明]
 * 禁止文字が含まれているかチェックします。
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * str   データ
 * taboo 禁止文字
 * [戻り]
 * 禁止文字が含まれていれば false、含まれていなければtrueを返します。
 </jp> */
function isTabooCheck(str,taboo)
{
	if( str == "" ) return true;
	if( taboo == "" ) return true;

	if( str.match("[" + taboo + "]")) {
		return false;
	} else {
		return true;
	}
}


/** <jp>
 * [関数の説明]
 * 禁止文字が含まれているかチェックします。このメソッドでは16進数に変換して比較します。
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * str   データ
 * taboo 禁止文字
 * [戻り]
 * 禁止文字が含まれていれば true、含まれていなければfalseを返します。
 </jp> */
function isProhibitedCharacter(str,taboo)
{
	rObj = new RegExp(taboo);

	if( str == "" ) return false;
	if( taboo == "" ) return false;

	oText= "";
	for (i=0; i<str.length; i++) {
		oText = str.charCodeAt(i).toString(16).toUpperCase();
		if( oText.match(rObj)) {
			return true;
		} else {

		}
	}
	return false;
}

/** <jp>
 * [関数の説明]
 * 16進数に変換した値を返します。
 *
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * str データ
 * [戻り]
 * １６進数に変換した値（スペース区切り）
 </jp> */
function textConv16(str) {
	oText= "";
	for (i=0; i<str.length; i++) {
		oText += str.charCodeAt(i).toString(16);
		oText +=" ";
	}
	return oText;
}


/** <jp>
 * [関数の説明]
 * データが数字(0-9)のみで構成されているかチェックします。
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * str データ
 * [戻り]
 * 数字以外が含まれていたら false、含まれていなければtrueを返します。
 </jp> */
function isNumberCheck(str)
{
	if( str == "" ) return true;

	if( str.match(/[^0-9]/) ) return false;

	return true;
}


/** <jp>
 * [関数の説明]
 * データが数値入力テキストボックスで使用できる文字のみで構成されているかチェックします。
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * str データ
 * decimal 少数部桁数
 * [戻り]
 * 使用できる文字以外が含まれていればfalse。含まれていなければtrueを返します。
 </jp> */
// カンマフォーマット「.」、「,」の扱いを逆にする場合は、control_forCommaFormat.jsを使用する
function isNumberTextBoxCheck(str, decimal)
{
	if( str == "" ) return true;

	if(str == "." || str == "-") return false;

	if( str.indexOf(".") >= 0 ) {
		if( str.indexOf( ".", (str.indexOf(".")+1)) >= 0 ) return false;
	}
	if( str.indexOf("-") >= 0 ) {
		if( str.indexOf( "-", (str.indexOf("-")+1)) >= 0 ) return false;
	}

	if(decimal == 0)
	{
		if( str.match(/[^0123456789-]/) ) return false;
	}
	else
	{
		if( str.match(/[^0123456789.-]/) ) return false;
	}

	if( isNaN(str) )
	{
		return false;
	}

	return true;
}


/** <jp>
 * [関数の説明]
 * データが数値入力テキストボックスで使用できる文字のみで構成されているかチェックします。
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * str データ
 * [戻り]
 * 使用できる文字以外が含まれていればfalse。含まれていなければtrueを返します。
 </jp> */
function isBarCodeTextBoxCheck(str)
{
	if( str == "" ) return true;

	if( !isHalfWidthCharacter(str) ) return false;

	if( str.match(/[^0-9]/) && str.match(/[^A-Z]/i) && str.match(/[^@_]/) )
	{
		var i = 0;
		for( i = 0; i<str.length; i++ ) {
			CLetter = escape(str.charAt(i)) ;
			if (( CLetter >= "%5B" && CLetter <= "%5E" ) || ( CLetter >= "%7B" && CLetter <= "%7E" )) {
			}else{
					return false;
			}
		}
	}
	return true;

}


/** <jp>
 * [関数の説明]
 * 指定範囲の文字列を抜き出します。
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * str 対象となる文字列
 * start 抜き出す先頭位置（ゼロが先頭）
 * end 抜き出す終了位置
 * [戻り]
 * 抜き出した文字列
 </jp> */
function getSubString(str, start, end)
{
	return str.substring( start, end );
}



/** <jp>
 * [関数の説明]
 * データが指定された文字種で構成されているかチェックします。
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * str   データ
 * kind  文字種
 *       1:全角文字 2:半角カナ 3:半角英字
 *       4:半角数字 5:半角記号 6:半角スペース
 *       7:禁止文字チェック
 * taboo 禁止文字
 * [戻り]
 * 指定された文字種以外が含まれていれば false、含まれていなければtrueを返します。
 </jp> */
function isCharacterCheck(str,kind,taboo)
{
	if( str == "" ) return true;
	if( kind == "" ) return true;

	//禁止文字を含んでいないかチェックします
	if( kind.indexOf("7") >= 0 ) {
		if( isProhibitedCharacter(str,taboo) ) return false;
	}

	//文字種に全角文字が含まれていればチェックはしません
	if( kind.indexOf("1") != -1 ) return true;

	//全角が含まれていないかチェックします
	if( !isHalfWidthCharacter(str) ) return false;

	//扱えない文字が含まれていないかチェック
	if( isContainInvalidCharacter(str) ) return false;

	//半角カナを含んでいないかチェック
	if( kind.indexOf("2") < 0 ) {
		if( isContainHalfWidthKANA(str) ) return false;
	}

	//半角英字を含んでいないかチェック
	if( kind.indexOf("3") < 0 ) {
		if( isContainAlphabet(str) ) return false;
	}

	//半角数字を含んでいないかチェック
	if( kind.indexOf("4") < 0 ) {
		if( isContainHalfWidthNumber(str) ) return false;
	}

	//半角記号を含んでいないかチェック
	if( kind.indexOf("5") < 0 ) {
		if( isContainHalfWidthSign(str) ) return false;
	}

	//半角スペースを含んでいないかチェック
	if( kind.indexOf("6") < 0 ) {
		if( isContainHalfWidthSpace(str) ) return false;
	}

	return true;
}


/** <jp>
 * [関数の説明]
 * データが日付として有効かチェックします。
 * 日付範囲:1970/1/1～2049/12/31
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * str    データ
 * format 書式
 * [戻り]
 * 日付として無効ならば false、有効ならばtrueを返します。
 </jp> */
function isDateCheck(str, format)
{
	if( str == "" ) return true;
	if( format == "" ) return true;

	var y = "";
	var m = "";
	var d = "";
	var y_i = 0;
	var m_i = 1;
	var d_i = 1;
	var uru = 28;

	if( str.match(/[^0-9]/) ) return false;
	if( str.length != format.length ) return false;
	for( i=0; i<str.length; i++) {
		if( format.charAt(i) == 'Y' || format.charAt(i) == 'y' ) {
			y += str.charAt(i);
		} else if( format.charAt(i) == 'M' || format.charAt(i) == 'm' ) {
			m += str.charAt(i);
		} else {
			d += str.charAt(i);
		}
	}

	if( format.match(/[yY]/) ) {
		if( y == "" ) return false;
		if( parseInt(y, 10) < 0 ) {
			return false;
		}

		y_i =  parseInt(y, 10);

		if( parseInt(y, 10) < 100 ) {
			if( parseInt(y, 10) < 70 ) {
				y_i += 2000;
			} else {
				y_i += 1900;
			}
		}

		if( y_i < 1970 || y_i > 2049 ) {
			return false;
		}

	} else {
		DATE = new Date();
		y_i = DATE.getFullYear();
	}

	if( format.match(/[mM]/) ) {
		if( m == "" ) return false;
		m_i = parseInt(m, 10);
		if( m_i < 1 || m_i > 12 ) return false;
	}

	if( format.match(/[dD]/) )
	{
		if( d == "" )
		{
			return false;
		}
		d_i = parseInt(d, 10);
	}
	else
	{
		if( d == "" )
		{
			return true;
		}
		else
		{
			return false;
		}
	}


	if( m_i == 2 ) {
		if( y_i % 4 == 0 ) {
			if( y_i % 100 == 0 ) {
				if( (y_i % 400)  == 0 ) {
					uru = 29;
				}
			} else {
				uru = 29;
			}
		}
		if( d < 1 || d > uru )
		{
			return false;
		}
	}

	else if( m_i == 4 || m_i == 6 || m_i == 9 || m_i == 11 )
	{
		if( d < 1 || d > 30 )
		{
			return false;
		}
	}
	else
	{
		if( d < 1 || d > 31 )
		{
			return false;
		}
	}

	return true;
}


/** <jp>
 * [関数の説明]
 * データが時間として有効かチェックします。
 * 時間範囲:00:00:00～23:59:59
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * str    データ
 * format 書式
 * [戻り]
 * 時間として無効ならば false、有効ならばtrueを返します。
 </jp> */
function isTimeCheck(str, format)
{
	if( str == "" ) return true;
	if( format == "" ) return true;

	var h = "";
	var m = "";
	var s = "";

	if( str.match(/[^0-9]/) ) return false;
	if( str.length != format.length ) return false;

	for( i = 0; i<str.length; i++ ) {
		if( format.charAt(i) == 'H' || format.charAt(i) == 'h' ) {
			h += str.charAt(i);
		} else if( format.charAt(i) == 'M' || format.charAt(i) == 'm' ) {
			m += str.charAt(i);
		} else {
			s += str.charAt(i);
		}
	}

	if( format.match(/[hH]/) ) {
		if( h == "" ) return false;
		if( parseInt(h, 10) < 0 || parseInt(h, 10) > 23 ) return false;
	}

	if( format.match(/[mM]/) ) {
		if( m == "" ) return false;
		if( parseInt(m, 10) < 0 || parseInt(m, 10) > 59 ) return false;
	}

	if( format.match(/[sS]/) ) {
		if( s == "" ) return false;
		if( parseInt(s, 10) < 0 || parseInt(s, 10) > 59 ) return false;
	} else {
		if( s != "" ) return false;
	}

	return true;
}


/** <jp>
 * [関数の説明]
 * 全角文字列精査。
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * str データ
 * [戻り]
 * 全角文字列のみであればtrue、それ以外はfalseを返します。
 </jp> */
function isFullWidthCharacter( str )
{
	var i = 0;
	for( i = 0; i<str.length; i++ ) {
		if( escape(str.charAt(i)).length >= 4 ) {
			CLetter = escape(str.charAt(i)) ;
			if( CLetter.indexOf("%uFF") != -1 && '0x'+CLetter.substring(2,CLetter.length) > 0xFF60 && '0x'+CLetter.substring(2,CLetter.length) < 0xFFA0 ) {
				return false;
			} else {
			}
		} else {
			return false;
		}
	}
	return true;
}


/** <jp>
 * [関数の説明]
 * 半角文字列精査。
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * str    データ
 * [戻り]
 * 半角文字列のみであればtrue。それ以外はfalseを返します。
 </jp> */
function isHalfWidthCharacter(str)
{
	var i = 0;
	for( i = 0; i<str.length; i++ ) {
		if( escape(str.charAt(i)).length >= 4 ) {
			CLetter = escape(str.charAt(i)) ;
			if( CLetter.indexOf("%uFF") != -1 && '0x'+CLetter.substring(2,CLetter.length) > 0xFF60 && '0x'+CLetter.substring(2,CLetter.length) < 0xFFA0 ) {
			} else {
				return false;
			}
		}
	}
	return true;
}


/** <jp>
 * [関数の説明]
 * 半角カナ文字が含まれるかどうかを精査。
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * str    データ
 * [戻り]
 * 半角カナ文字が含まれない場合はfalse、含まれる場合はtrueを返します。
 </jp> */
function isContainHalfWidthKANA(str)
{
	var i = 0;
	for( i = 0; i<str.length; i++ ) {
		if( escape(str.charAt(i)).length >= 4 ) {
			CLetter = escape(str.charAt(i)) ;
			if( CLetter.indexOf("%uFF") != -1 && '0x'+CLetter.substring(2,CLetter.length) > 0xFF60 && '0x'+CLetter.substring(2,CLetter.length) < 0xFFA0 ) {
				return true;
			}
		}
	}
	return false;
}


/** <jp>
 * [関数の説明]
 * 半角英字が含まれるかどうかを精査。
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * str    データ
 * [戻り]
 * 半角英字が含まれない場合はfalse、含まれる場合はtrueを返します。
 </jp> */
function isContainAlphabet(str)
{
	var i = 0;

	if( str.match(/[A-Z]/i) || str.match(/[@_]/) ) return true;

	for( i = 0; i<str.length; i++ ) {
		CLetter = escape(str.charAt(i)) ;
		// ver 4.3.2.0 %60を英字として扱うように修正 t.ogawa 2008/04/03
		if (( CLetter >= "%5B" && CLetter <= "%5E" ) || CLetter == "%60" || ( CLetter >= "%7B" && CLetter <= "%7E" )) {
				return true;
		}
	}
	return false;
}


/** <jp>
 * [関数の説明]
 * 半角数字が含まれるかどうかを精査。
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * str    データ
 * [戻り]
 * 半角数字が含まれない場合はfalse、含まれる場合はtrueを返します。
 </jp> */
function isContainHalfWidthNumber(str)
{
	if( str.match(/[0-9]/) ) return true;

	return false;
}


/** <jp>
 * [関数の説明]
 * 半角記号が含まれるかどうかを精査。
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * str    データ
 * [戻り]
 * 半角記号が含まれない場合はfalse、含まれる場合はtrueを返します。
 </jp> */
function isContainHalfWidthSign(str)
{
	if( str.match(/[*+-.\/]/) ) return true;

	for( i = 0; i<str.length; i++ ) {
		CLetter = escape(str.charAt(i)) ;
		if (( CLetter >= "%21" && CLetter <= "%29" ) || CLetter == "%2C" || ( CLetter >= "%3A" && CLetter <= "%3F" )) {
				return true;
		}
	}

	return false;
}

/** <jp>
 * [関数の説明]
 * 半角スペースが含まれるかどうかを精査。
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * str    データ
 * [戻り]
 * 半角スペースが含まれない場合はfalse、含まれる場合はtrueを返します。
 </jp> */
function isContainHalfWidthSpace(str)
{
	if( str.match(/[ ]/) ) return true;

	return false;
}

/** <jp>
 * [関数の説明]
 * 扱えない文字が含まれるかどうかを精査。
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * str    データ
 * [戻り]
 * 扱えない文字が含まれない場合はfalse、含まれる場合はtrueを返します。
 </jp> */
function isContainInvalidCharacter(str)
{
	if( str.match(/[^A-Z]/i) && str.match(/[^@_]/) && str.match(/[^0-9]/) && str.match(/[^ ]/) && str.match(/[^`*+-.\/]/) )
	{
		for( i = 0; i<str.length; i++ )
		{
			CLetter = escape(str.charAt(i));

			if( escape(str.charAt(i)).length >= 4 )
			{
				CLetter = escape(str.charAt(i));
				if( CLetter.indexOf("%uFF") != -1 && '0x'+CLetter.substring(2,CLetter.length) > 0xFF60 && '0x'+CLetter.substring(2,CLetter.length) < 0xFFA0 )
				{
				}
				else
				{
					return true;
				}
			}
			else
			{
				if( str.charAt(i).match(/[A-Z]/i) || str.charAt(i).match(/[@_]/) || str.charAt(i).match(/[0-9]/) || str.charAt(i).match(/[ ]/) || str.charAt(i).match(/[*+-.\/]/) )
				{
					// ver 4.3.2.0 %60を扱える文字にするように修正 t.ogawa 2008/04/03
				}
				else if (( CLetter >= "%21" && CLetter <= "%29" ) || CLetter == "%2C" || ( CLetter >= "%3A" && CLetter <= "%3F" ) ||
					     ( CLetter >= "%5B" && CLetter <= "%5E" ) || CLetter == "%60" || ( CLetter >= "%7B" && CLetter <= "%7E" || CLetter == "%A0"))
				{
				}
				else
				{
					return true;
				}
			}
		}
	}
	return false;
}


/** <jp>
 * [関数の説明]
 * 日付のフォーマットを変更します。
 * 変更前書式のデータを変更後書式に変更します。
 * 変更後の書式で必要な情報が、変更前の書式に無い場合は、
 * 現在日時で補います。
 * 日付範囲:1970/1/1～2049/12/31
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * str     データ
 * bformat 変更前書式
 * aformat 変更後書式
 * delim   書式内の日付区切り文字（"/","-"等）
 * [戻り]
 * 変更後書式に変更したデータを返します。
 </jp> */
function changeDateFormat(str, bformat, aformat, delim)
{

	if( str == "" || aformat == "" ) return str;

	if( bformat == "" ){
		if( aformat.match(/[\/]/) ){
			return getFormat(str,aformat,"/");
		} else {
			return getString(str,"/");
		}
	}

	for( i = 0; i<str.length; i++ ) {
		if( str.charAt(i) != delim && str.charAt(i).match(/[^0123456789]/) ) {
			return str;
		}
	}
	if( str.length != bformat.length ) return str;

	var dtoday = new Date();

	var y = "";
	var m = "";
	var d = "";
	var y_i = dtoday.getYear();
	var m_i = dtoday.getMonth() + 1;
	var d_i = dtoday.getDate();
	var y_len = 0;
	var y_flg = false;
	var reStr = "";

	for( i=0; i<str.length; i++) {
		if( bformat.charAt(i) == 'Y' || bformat.charAt(i) == 'y' ) {
			y += str.charAt(i);
		} else if( bformat.charAt(i) == 'M' || bformat.charAt(i) == 'm' ) {
			m += str.charAt(i);
		} else if( bformat.charAt(i) == 'D' || bformat.charAt(i) == 'd' ) {
			d += str.charAt(i);
		}
	}

	if( y != "" ) y_i = parseInt(y, 10);
	if( m != "" ) m_i = parseInt(m, 10);
	if( d != "" ) d_i = parseInt(d, 10);

	if( parseInt(y, 10) < 100 ) {
		if( parseInt(y, 10) < 70 ) {
			y_i += 2000;
		} else {
			y_i += 1900;
		}
	}

	y = new String( y_i );
	m = new String( m_i );
	d = new String( d_i );

	for( i=0; i<aformat.length; i++) {
		if( aformat.charAt(i) == 'Y' || aformat.charAt(i) == 'y' ) {
			y_len++;
			y_flg = true;
		} else if( aformat.charAt(i) == 'M' || aformat.charAt(i) == 'm' ) {
			if( y_flg && y_len > 0 ){
				reStr += y.substring( (4 - y_len), y.length );
				y_len = 0;
			}
			m = "0" + m;
			reStr += m.substring( (m.length - 2), m.length );
			i += 1;
		} else if( aformat.charAt(i) == 'D' || aformat.charAt(i) == 'd' ) {
			if( y_flg && y_len > 0 ){
				reStr += y.substring( (4 - y_len), y.length );
				y_len = 0;
			}
			d = "0" + d;
			reStr += d.substring( (d.length - 2), d.length );
			i += 1;
		} else {
			if( y_flg && y_len > 0 ){
				reStr += y.substring( (4 - y_len), y.length );
				y_len = 0;
			}
			reStr += aformat.charAt(i);
		}
	}

	if( y_flg && y_len > 0 ){
//		reStr += y.substring( (4 - y_len), y_len );
		reStr += y.substring( (4 - y_len), 4 );
	}

	return reStr;
}


/** <jp>
 * [関数の説明]
 * 時間のフォーマットを変更します。
 * 変更前書式のデータを変更後書式に変更します。
 * 変更後の書式で必要な情報が、変更前の書式に無い場合は、
 * 現在日時で補います。
 * 時間範囲:00:00:00-23:59:59
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * str     データ
 * bformat 変更前書式
 * aformat 変更後書式
 * delim   書式内の時間区切り文字（:等）
 * [戻り]
 * 変更後書式に変更したデータを返します。
 </jp> */
function changeTimeFormat(str, bformat, aformat, delim)
{
	if( str == "" || aformat == "" ) return str;

	if( bformat == "" ){
		if( str.match(/[^0123456789]/) ){
			return getFormat(str,aformat,delim);
		} else {
			return getString(str,delim);
		}
	}

	for( i = 0; i<str.length; i++ ) {
		if( str.charAt(i) != delim && str.charAt(i).match(/[^0123456789]/) ) {
			return str;
		}
	}
	if( str.length != bformat.length ) return str;

	var dtoday = new Date();

	var h = "";
	var m = "";
	var s = "";
	var h_i = dtoday.getHours();
	var m_i = dtoday.getMinutes();
	var s_i = dtoday.getSeconds();
	var reStr = "";

	for( i=0; i<str.length; i++) {
		if( bformat.charAt(i) == 'H' || bformat.charAt(i) == 'h' ) {
			h += str.charAt(i);
		} else if( bformat.charAt(i) == 'M' || bformat.charAt(i) == 'm' ) {
			m += str.charAt(i);
		} else if( bformat.charAt(i) == 'S' || bformat.charAt(i) == 's' ) {
			s += str.charAt(i);
		}
	}

	if( h != "" ) h_i = parseInt(h, 10);
	if( m != "" ) m_i = parseInt(m, 10);
	if( s != "" ) s_i = parseInt(s, 10);

	h = new String( h_i );
	m = new String( m_i );
	s = new String( s_i );

	for( i=0; i<aformat.length; i++) {
		if( aformat.charAt(i) == 'H' || aformat.charAt(i) == 'h' ) {
			h = "0" + h;
			reStr += h.substring( (h.length - 2), h.length );
			i += 1;
		} else if( aformat.charAt(i) == 'M' || aformat.charAt(i) == 'm' ) {
			m = "0" + m;
			reStr += m.substring( (m.length - 2), m.length );
			i += 1;
		} else if( aformat.charAt(i) == 's' || aformat.charAt(i) == 's' ) {
			s = "0" + s;
			reStr += s.substring( (s.length - 2), s.length );
			i += 1;
		} else {
			reStr += aformat.charAt(i);
		}
	}

	return reStr;
}


/** <jp>
 * [関数の説明]
 * エレメント要素をたどりコントロールの色をハイライト表示します。
 *
 * @param el - コントロールの色を変更する必要のあるテーブルエレメント
 </jp> */
function changeHighlight(el)
{
	var hilightId     = "SpanFlag0";

	if ( el != null || el != undefined )
	{
		var elementList = el.childNodes;

		if ( elementList.length != undefined )
		{
			for (var i = 0; i < elementList.length; i++ )
			{
				var element = elementList[i];
				var elem = element.lastChild;

				if ( element != null && element != undefined &&
				     ( element.tagName == "TD" ) &&
				     (( element.id.indexOf( hilightId ) == -1 ) &&
				      ( element.id != "" )
				     )
				   )
				{
					continue;
				}
				else if ( ( element.fixed != null && element.fixed != undefined ) &&
						  ( element.fixed == 'true' || element.fixed == true )
						)
				{
					continue;
				}

				cellColors[cellColors.length] = element.bgColor;

				element.bgColor = _rowBgColor;

				if ( elem != null && elem != undefined &&
				   ( elem.tagName == "SPAN" || elem.tagName == "A" ) )
				{
					var elementId = elem.id;
					cssArray[elementId] = elem.className;
					styleColorArray[elementId] = elem.style.color;
					elem.bgColor = _rowBgColor;
					elem.style.color = _rowStyleColor;
				}
			}
		}
	}
}

/** <jp>
 * [関数の説明]
 * 矢印キーによる移動と判断するフラグをクリアします。
 </jp> */
function clearArrowMove()
{
	arrowMove = false;
}

var defaultBgColor = "";
var defaultSytleColor = "";
var cssArray = new Array();
var styleColorArray = new Array();
var cellColors = new Array();
var cellColorNum = 0;

var nowRowId = "";
var arrowMove = false;
var isArrow = false;

/** <jp>
 * [関数の説明]
 * 矢印キーによる移動と判断するフラグをクリアします。
 </jp> */
function checkHighlight( which, target , scrollFlag )
{
	if( arrowMove )
	{
		return;
	}

	var el = getElement(event.srcElement, "TR", "TD");
	var el_target="";

	if(target!=null)
	{
		el_target = document.getElementById(target);
	}

	if (el == null)
	{
		return;
	}

	if (which && el.tagName == "TD")
	{
		var row = getElement(el, "TR");

		setHighlightColor( row, el_target , scrollFlag );
	}
}


/** <jp>
 * [関数の説明]
 * リストセルでのハイライト表示を元に戻します。
 * 矢印キーからの操作であれば、Timerを使用せずrestoreを行うのでarrowMoveフラグをクリアします。
 *
 * [引数の説明]
 * isArrow 矢印キーの移動であればtrue
 *         テーブルのonmouse関数であればfalse
 * target  type3で、対となる明細テーブル
 * scrollFlag  type3で、左の明細であれば'left'
 *                      右の明細であれば'right'
 * listcellId  対象のリストセルのID
 </jp> */
function restoreHighlight( isArrow , target , scrollFlag , listcellId )
{
	if( isArrow )
	{
		clearArrowMove();
	}
	else
	{
		cellColorNum=0;
	}

	if( arrowMove)
	{
		return;
	}

	if( nowRowId == "" )
	{
		return;
	}
	var el_target ="";
	if(target != null)
	{
		if( listcellId == '' )
		{
			el_target = document.getElementById(target);
		}
		else
		{
			var tmpNowRowId  = document.getElementById( listcellId +'__TABLE__LEFT__DETAIL' );
			for(var i = 0 ; i < tmpNowRowId.rows.length ; i ++ )
			{
				if( nowRowId.id == tmpNowRowId.rows[ i ].id )
				{
					nowRowId = tmpNowRowId.rows[ i ];
					break;
				}
			}
			el_target = document.getElementById( listcellId + '__TABLE__MAIN__DETAIL' );
		}
	}
	var row = nowRowId;

	var table = getElement(row, "TABLE");
	var elm = row.parentElement;

	if ( table == null )
	{
		return;
	}

	if ( (row.tagName == "TR") &&
		 ( row.parentElement != null &&
		   row.parentElement.tagName == "THEAD"
		 )
	   )
	{
		return;
	}

	var rowArray = getRow( row.id, table );
	var rowArrayTarget = "";

	if(el_target!="")
	{
		rowArrayTarget = getRow( row.id , el_target);
	}

	if (rowArray == undefined)
	{
		return;
	}

	for (var i = 0; i < rowArray.length; i++)
	{
		if( scrollFlag == 'left')
		{
			changeDefault(rowArray[i], i);
			changeDefault(rowArrayTarget[i], cellColorNum );
		}
		else if( scrollFlag == 'right')
		{
			changeDefault(rowArray[i], i);
			changeDefault(rowArrayTarget[i], cellColorNum );
		}
		else
		{
			changeDefault(rowArray[i], i);
		}
	}

	cellColors = new Array();
	cellColorNum = 0;
}


/** <jp>
 * [関数の説明]
 * リストセルでハイライト表示します。
 * [引数の説明]
 * target 対象となるリストセル
 * tablePos  type3で、対となる明細テーブル
 * scrollFlag  type3で、左の明細であれば'left'
 *                      右の明細であれば'right'
 </jp> */
function focusHighlight( target , tablePos , scrollFlag )
{
	var el = getElement(target, "TR", "TD");

	if (el == null)
	{
		return;
	}

	if (el.tagName == "TD")
	{
		var row = getElement(el, "TR");

		setHighlightColor( row, tablePos , scrollFlag );
	}
}

/** <jp>
 * [関数の説明]
 * リストセルでハイライト表示します。
 * [引数の説明]
 * row  対象となる行
 * el_target  type3で、対となる明細テーブル
 * scrollFlag  type3で、左の明細であれば'left'
 *                      右の明細であれば'right'
 </jp> */
function setHighlightColor( row, el_target , scrollFlag )
{
	var table = getElement(row, "TABLE");
	var elm = row.parentElement;

	if ( table == null )
	{
		return;
	}
	if ( row.id =="" )
	{
		return;
	}

	if ( (row.tagName == "TR") &&
		 ( row.parentElement != null &&
		   row.parentElement.tagName == "THEAD"
		 )
	   )
	{
		return;
	}

	if ( row.id != "" )
	{
		nowRowId = row;
	}

	var rowArray = getRow( row.id, table );
	var rowArrayTarget = "";

	if(el_target!="")
	{
		rowArrayTarget = getRow( row.id , el_target);
	}

	if (rowArray == undefined)
	{
		return;
	}

	for (var i = 0; i < rowArray.length; i++)
	{
		if( scrollFlag == 'left')
		{
			changeHighlight(rowArray[i], i);

			if( el_target!="")
			{
				changeHighlight(rowArrayTarget[i], i);
			}
		}
		else if( scrollFlag == 'right')
		{

			if(el_target!="")
			{
				changeHighlight(rowArrayTarget[i], i);
			}
			changeHighlight(rowArray[i], i);
		}
		else
		{
			changeHighlight(rowArray[i], i);

		}
	}
}

/**
<jp> * [関数の説明]</jp>
<jp> * テーブルの行情報を取得します。</jp>
<jp> * [引数の説明]</jp>
<jp> * rowId 対象の行のID</jp>
<jp> * table 対象のテーブル</jp>
<jp> * [戻り]</jp>
<jp> * テーブルの行情報</jp>
 */
function getRow( rowId, table )
{
	var ROWMAX = table.rows.length;
	var ret = new Array();

	for ( var i = 0; i < ROWMAX; i++ )
	{
		if ( table.rows[i].id == rowId )
		{
			ret[ret.length] = table.rows[i];
		}
	}

	return ret;
}

/**
<jp> * [関数の説明]</jp>
<jp> * エレメント要素をたどりコントロールの色を元にもどします。</jp>
<jp> * [引数の説明]</jp>
<jp> * el 対象のエレメント</jp>
<jp> * rowNum 対象の行</jp>
 */
function changeDefault(el, rowNum)
{
	var hilightId     = "SpanFlag0";

	if ( el != null || el != undefined )
	{
		var elementList = el.childNodes;

		if( rowNum == 0 )
		{
			cellColorNum = rowNum;
		}

		if ( elementList.length != undefined )
		{
			var k = 0;

			if( rowNum > 0 )
			{
				k = cellColorNum;
			}
			for (var i = 0; i < elementList.length; i++ )
			{
				var element = elementList[i];
				var elem = element.lastChild;

				if ( element != null && element != undefined &&
				     ( element.tagName == "TD" ) &&
				     (( element.id.indexOf( hilightId ) == -1 ) &&
				      ( element.id != "" ) )
				   )
				{
					continue;
				}
				else if ( ( element.fixed != null && element.fixed != undefined ) &&
						  ( element.fixed == 'true' || element.fixed == true )
						)
				{
					continue;
				}

				element.bgColor = cellColors[k];
				k++;
// Mod Start 2010/02/23 Sumiyama for v5.2 ボタンコントロールだった場合を省きます
				if ( elem != null && elem != undefined &&
				   ( (elem.tagName == "SPAN" && !elem.className == "cellSpan-Buttons") || elem.tagName == "A" ) )
//				if ( elem != null && elem != undefined &&
//						   ( elem.tagName == "SPAN" || elem.tagName == "A" ) )
//						{
// Mod End 2010/02/23 Sumiyama for v5.2
				{
					var elementId = elem.id;
					elem.className = cssArray[elementId];
					elem.style.color = styleColorArray[elementId];
				}
			}
			cellColorNum = k;
		}
	}
}

/**
<jp> * [関数の説明]</jp>
<jp> * エレメント要素をたどり親エレメントを取得します。</jp>
<jp> * [引数の説明]</jp>
<jp> * el 対象のエレメント</jp>
 */
function getElement(el)
{
    var tagList = new Object;
    for (var i = 1; i < arguments.length; i++)
    {
        tagList[arguments[i]] = true;
    }
    while ((el != null) && (tagList[el.tagName] == null))
    {
        el = el.parentElement;
    }
    return el;
}

/** <jp>
 * [関数の説明]
 * Pagerの画像変更用関数
 * [引数の説明]
 * event       マウスイベント
 * id          Id
 * btnno       Pagerイベント（ff, prev, nxt, last)
 * contextPath ドキュメントルート
 </jp> */
function change_PageBtn(event, id, btnno, contextPath)
{
	switch(event)
	{
		case 'over':
		{
			document.images[id].src = contextPath + "/img/control/pager/tbtn_" + btnno + "_mo.gif";
			break;
		}
		case 'out':
		{
			document.images[id].src = contextPath + "/img/control/pager/tbtn_" + btnno + "_rglr.gif";
			break;
		}
		case 'down':
		{
			document.images[id].src = contextPath + "/img/control/pager/tbtn_" + btnno + "_oc.gif";
			break;
		}
		case 'up':
		{
			document.images[id].src = contextPath + "/img/control/pager/tbtn_" + btnno + "_rglr.gif";
			break;
		}
		case 'click':
		{
			document.images[id].src = contextPath + "/img/control/pager/tbtn_" + btnno + "_oc.gif";
			break;
		}
	}
}

// Add 2008/05/13 v4.3.1.0
/** <jp>
 * [関数の説明]
 * マウスのホイールイベントを制御します。
 * [引数の説明]
 *
 </jp> */
function invalidateWheel()
{
if ( window.event.shiftKey )
	{
		if (window.event.preventDefault)
		{
			window.event.preventDefault();
		}
		window.event.cancelBubble = true;
		window.event.returnValue = false;
	}
}
if (window.addEventListener)
	window.addEventListener('DOMMouseScroll', invalidateWheel, false);
document.onmousewheel = invalidateWheel;
// End 2008/05/13 v4.3.1.0

/** <jp>
 * [関数の説明]
 * ショートカットキー入力を制御します。
 * [戻り]
 * ショートカットキーを無効にする場合falseを返します。
 </jp> */
function ignoreKeyEvent()
{
	/*
	 keycode : key
	 32      : space
	 36      : Home
	 37      : <-
	 39      : ->
	 115     : F4
	 90      : z
	*/
// Mod Start 2010/02/23 T.Sumiyama for v5.2 (add "90[z]")
	var invalidateAltKey = new Array("32", "36", "37", "39", "115", "90");
// Mod Start 2010/02/23 T.Sumiyama for v5.2
	/*
	 keycode : key
	 9       : Tab
	 53,101  : 5
	 65      : A
	 66      : B
	 67      : C
	 68      : D
	 69      : E
	 70      : F
	 72      : H
	 73      : I
	 74      : J
	 76      : L
	 78      : N
	 79      : O
	 80      : P
	 82      : R
	 86      : V
	 87      : W
	 88      : X
	 115     : F4
	 116     : F5
	 122     : F11
	 118     : F7
	*/
// Mod Start 2010/02/23 T.Sumiyama for v5.2 (add "118[F7]")
	var invalidateCtrlKey = new Array("9","53","101","65","66","68","69","70","72",
			"73","74","76","78","79","80","82","87","115","116","122","118");
// Mod End 2010/02/23 T.Sumiyama for v5.2

	/*
	 keycode  : key
	 114      : F3
	 115      : F4
	 116      : F5
	 117      : F6
	 121      : F10
	 122      : F11
	 118      : F7
	 123      : F12
	*/
// Mod Start 2010/02/23 T.Sumiyama for v5.2 (add "118[F7], 123[F12]")
	var invalidateFunctionKey = new Array("114","115","116","117","121","122","118", "123");
// Mod End 2010/02/23 T.Sumiyama for v5.2


// Add Start 2010/02/23 T.Sumiyama for v5.2 (add new var invalidateCtrlShiftKey)
	/*
	 keycode : key
	 46      : del
	 80      : P
	*/
	var invalidateCtrlShiftKey = new Array("46", "80");
// Add End 2010/02/23 T.Sumiyama for v5.2

	//ESC key
	if( window.event.keyCode  == '27' )
	{
		return false;
	}

	// ALT key down
	if (window.event.altKey)
	{
		return invalidateKeyOperation(invalidateAltKey);
	}
	// Ctrl key down
	if (window.event.ctrlKey)
	{
// Add Start 2010/02/23 T.Sumiyama for v5.2 shift + ctrl + key
		if (window.event.shiftKey)
		{
			return invalidateKeyOperation(invalidateCtrlShiftKey);
		}
// Add End 2010/02/23 T.Sumiyama for v5.2
		return invalidateKeyOperation(invalidateCtrlKey);
	}

	//BackSpaceKey
	if( window.event.keyCode  == '8' )
	{
		//<jp> インプットタグ、テキストエリアタグ上の場合</jp>
		if( event.srcElement.tagName == "INPUT" || event.srcElement.tagName == "TEXTAREA" )
		{
			//<jp> テキスト、パスワード、ファイルの場合</jp>
			if( event.srcElement.type == "text" || event.srcElement.type == "password" ||
				event.srcElement.type == "file" || event.srcElement.type == "textarea" )
			{
				//<jp> readOnlyで無い場合</jp>
				if( event.srcElement.readOnly == false )
				{
					//<jp> イベント有効</jp>
					return true;
				}
				else
				{
					//<jp> イベント無効</jp>
					window.event.keyCode = 37;
					return false;
				}
			}
			else
			{
					//<jp> イベント無効</jp>
					window.event.keyCode = 37;
					return false;
			}
		}
		else
		{
			//<jp> イベント無効</jp>
			window.event.keyCode = 53;
			return false;
		}
	}

	//Function Key
	return invalidateKeyOperation(invalidateFunctionKey);
}


/** <jp>
 * [関数の説明]
 * ショートカットキー入力を制御するための関数で、ignoreKeyEventからコールされます。
 * [引数の説明]
 * invalidateKey 無効にしたショートカットキー一覧
 * [戻り]
 * ショートカットキーを無効にする場合falseを返します。
 </jp> */
function invalidateKeyOperation(invalidateKey)
{
	for (var i = 0; i < invalidateKey.length; i++)
	{
		if (window.event.keyCode == invalidateKey[i])
		{
			try
			{
				window.event.keyCode = 37;
				return false;
			}
			catch(e)
			{
			}
		}
	}
}

/**
<jp> * [関数の説明]</jp>
<jp> * メッセージエリアをクリアします</jp>
 */
function clearMessage()
{
//	if(document.getElementById('message') != null)
//	{
//		document.getElementById('message').innerText = "";
//	}
	message.innerText = "";
}


/**
<jp> * [関数の説明]</jp>
<jp> * isValueInput関数から呼ばれます。</jp>
<jp> * 引数の文字列から半角空白を取り除き、文字列のサイズが0の場合はfalseを返します。</jp>
<jp> * 全角空白は1文字と数えるためその場合、trueを返します。</jp>
<jp> * [使用するカスタムタグ名]</jp>
 * SubmitButtonTag
<jp> * [引数の説明]</jp>
<jp> * value チェックを行う値</jp>
 */
function validate(value)
{
	while (value.indexOf(" ") > -1)
	{
		value = value.replace(" ", "");
	}
	if (value.length == 0)
	{
		return false;
	}
	return true;
}


/**
<jp> * [関数の説明]</jp>
<jp> * 連想配列文字列をArray型にして返します。</jp>
 * string                         Array
 * --------------------------     ----------------------------
 * "key1:value1,key2:value2"  ->  array[0][0] = "key1"
 *                                array[0][1] = "value1"
 *                                array[1][0] = "key2"
 *                                array[1][1] = "value2"
 *
<jp> * [引数の説明]</jp>
<jp> * str 連想配列文字列</jp>
 */
function toArray(str)
{
	var item = str.split(",");
	var array = new Array(item.length);
	for (i in item)
	{
		var relation = item[i].split(":");
		if (relation.length == 2)
		{
			array[i] = new Array(relation[0], relation[1]);
		}
		else if (relation.length == 3)
		{
			array[i] = new Array(relation[0], relation[1], relation[2]);
		}
	}
	return array;
}


/**
<jp> * [関数の説明]</jp>
<jp> * メッセージエリアへメッセージをセットします。</jp>
<jp> * [引数の説明]</jp>
<jp> * MessageResourceのメッセージ</jp>
 */
function setMessage(msg)
{
		if(!document.all.message)return;
		document.all.message.innerHTML =
		"<img  src=\"" + contextPath + "/img/control/message/Spacer.gif\" "+
		"width=\"4\" height=\"19\">" +
		msg;
}


/**
<jp> * [関数の説明]</jp>
<jp> * 画面のサイズを変更します。Window.open時にサイズを指定しても、指定したサイズよりも</jp>
<jp> * 大きなサイズで開くため、resizeTo関数を使用する必要があります。</jp>
<jp> * サイズの定義はuser.jsにて行います。定義が無い場合は幅1024、高さ768でリサイズします。</jp>
 */
function windowResize()
{
	if (typeof _mainWindowWidth == "undefined"){
		_mainWindowWidth = 1024;
	}
	if (typeof _mainWindowHeight == "undefined"){
		_mainWindowHeight = 768;
	}
	var w = _mainWindowWidth;
	var h = _mainWindowHeight;
	var x = (screen.availWidth  - w) / 2;
	var y = (screen.availHeight - h) / 2;
	// If the width is less than 1024,
	if( screen.availWidth < w )
	{
		x = 0;
		w = screen.availWidth;
	}
	// If the height is less than 768,
	if( screen.availHeight < h )
	{
		y = 0;
		h = screen.availHeight;
	}

	try
	{
		window.resizeTo(w,h);
		window.moveTo(x,y);
	}
	catch( e )
	{
	}
}


/**
<jp> * [関数の説明]</jp>
<jp> * JSPに定義されたDynamicAction関数を呼び出します。JSPに定義されていない場合はエラーとせずに、そのまま抜けます。</jp>
 */
function callDynamicAction()
{
	if(typeof dynamicAction != 'undefined'){
		if(typeof targetElement != 'undefined'){
			// FunctionKey不具合対応での修正開始 2008/08/26 v4.3.1.2
			if (event.type == 'click')
			{
				targetElement = event.srcElement.id;
			}

			/*
			if (targetElement == "")
			{
				targetElement = event.srcElement.id;
 			}
			*/
			// FunctionKey不具合対応での修正終了 2008/08/26 v4.3.1.2
		}
		if(dynamicAction()) event.returnValue = false;

 		if(typeof targetElement != 'undefined'){
			targetElement = "";
		}
	}
	return true;
}


/**
<jp> * [関数の説明]</jp>
<jp> * headerとdetailのテーブルの幅を同期させ、scrollの位置を修正します。</jp>
<jp> * [引数の説明]</jp>
<jp> * header ヘッダテーブル</jp>
<jp> * detail 明細テーブル</jp>
<jp> * scroll スクロールバー用<div></jp>
 */
function synchronizedTable( header, detail, scroll )
{
	try
	{
		//<jp> IEでない場合は処理を行いません。</jp>
		// 2010/10/04 IEのみの対応を解除
//		if( navigator.userAgent.indexOf("MSIE") == -1 )
//		{
//			retrun;
//		}

		//<jp> テーブルの表示状態の同期</jp>
		if ( document.all( header ) == null ||
		     document.all( header ).style.visibility == 'hidden' )
		{
			if ( document.all( detail ) != null )
			{
				document.all( detail ).style.visibility = 'hidden';
				document.all( scroll ).style.visibility = 'hidden';
			}
			return;
		}

		if ( document.all( detail ) == null ||
		     document.all( detail ).style.visibility == 'hidden' )
		{
			if ( document.all( header ) != null )
			{
				document.all( header ).style.visibility = 'hidden';
				document.all( scroll ).style.visibility = 'hidden';
			}
			return;
		}

		//<jp> 同期処理の開始</jp>
		var headerCells = document.all( header ).rows[0].cells;
		var detailCells = document.all( detail ).rows[0].cells;

		//<jp> セルの幅を一つずつ比較してWidthを補正していきます。</jp>
		for ( var i = headerCells.length-1; i >= 0; i-- )
		{
			var headerWidth = headerCells[i].offsetWidth;
			var detailWidth = detailCells[i].offsetWidth;
			var maxWidth = 0;

			//<jp> 同じ場合は次のセルへ</jp>
			if ( headerWidth  == detailWidth )
			{
				continue;
			}

			//<jp> headerのwidthが大きい場合。</jp>
			if ( headerWidth > detailWidth )
			{
				maxWidth = headerWidth;
			}
			//<jp> detailのwidthが大きい場合。</jp>
			else
			{
				maxWidth = detailWidth;
			}

			//<jp> 補正値の割り当て</jp>
			document.all( header ).rows[0].cells[i].width = maxWidth;
			document.all( detail ).rows[0].cells[i].width = maxWidth;
		}

		//<jp> テーブル最大サイズの同期（小さいほうに合わせる）</jp>
		var headerTableWidth = document.all( header ).offsetWidth;
		var detailTableWidth = document.all( detail ).offsetWidth;

		//<jp> headerのwidthが大きい場合。</jp>
		if ( headerTableWidth > detailTableWidth )
		{
			//<jp> headerのwidthをdetailのwidthに合わせる。</jp>
			document.all( header ).style.width = detailTableWidth;
		}
		//<jp> detailのwidthが大きい場合。</jp>
		else if ( headerTableWidth < detailTableWidth )
		{
			//<jp>detailのwidthをheaderのwidthに合わせる。</jp>
			document.all( detail ).style.width = headerTableWidth;
		}

		//<jp> スクロールバーの幅の取得</jp>
		//<jp> 物理サイズ - クライアントでの描画サイズ</jp>
		var scrollbarOffSet = document.all( scroll ).offsetWidth - document.all( scroll ).clientWidth;

		//<jp> スクロール用DIVの幅の修正</jp>
		//<jp> detailのwidth + スクロールバーの幅</jp>
		document.all( scroll ).style.width = document.all( detail ).offsetWidth + scrollbarOffSet + "px";
	}
	catch( e )
	{
	}
}

/**
<jp> * [関数の説明]</jp>
<jp> * userAgentからWinodwsXPSP2か判断します。</jp>
<jp> * WinodwsXPSP2の場合はtrueを返却します。それ以外の場合はfalseを返却します。</jp>
 */
function isWindowsXPSP2()
{
	return ( navigator.userAgent.indexOf("SV1") != -1 );

}


/**
<jp> * [関数の説明]</jp>
<jp> * 1ミリ秒後に指定されたidのコントロールからフォーカスはずします。</jp>
<jp> * [引数の説明]</jp>
<jp> * elemId フォーカスをはずしたいエレメントのid</jp>
 */
function timeoutBlur( elemId )
{
	try
	{
		setTimeout("setBlurTimer('"+elemId+"')",1 );
	}
	catch(e)
	{
	}
}

/**
<jp> * [関数の説明]</jp>
<jp> * 1ミリ秒後に指定されたidのコントロールからフォーカスはずします。</jp>
<jp> * [引数の説明]</jp>
<jp> * elemId フォーカスをはずしたいエレメントのid</jp>
 */
function setBlurTimer( elemId )
{
    document.all(elemId).blur();
}

/**
<jp> * [関数の説明]</jp>
<jp> * ListCellType3でheaderとdetailのテーブルの幅を同期させ、divの位置を修正します。</jp>
<jp> * [引数の説明]</jp>
<jp> * detail スクロール明細 テーブルID</jp>
<jp> * scroll スクロール明細DIV ID</jp>
 */
function synchronizedScrollBarSizeFixedSide( detail, scroll )
{
	//<jp> スクロールバーの幅の取得</jp>
	//<jp> 物理サイズ - クライアントでの描画サイズ</jp>
	var scrollbarOffSetWidth = document.all( scroll ).offsetWidth - document.all( scroll ).clientWidth;
	//<jp> スクロールバーの高さの取得</jp>
	var scrollbarOffSetHeight = document.all( scroll ).offsetHeight - document.all( scroll ).clientHeight;

	//<jp> スクロール用DIVの幅の修正</jp>
	//<jp> detailのwidth + スクロールバーの幅</jp>
	document.all( scroll ).style.width = document.all( scroll ).offsetWidth + scrollbarOffSetWidth + "px";
	document.all( scroll ).style.height = document.all( scroll ).offsetHeight + scrollbarOffSetHeight + "px";

	if( document.all( scroll ).offsetWidth > document.all( detail ).offsetWidth )
	{
		document.all( scroll ).style.width = 0;
		document.all( scroll ).style.overflowX="hidden";
	}
	else
	{
		document.all( scroll ).style.width = document.all( detail ).offsetWidth;
	}

	if( document.all( scroll ).offsetHeight > document.all( detail ).offsetHeight )
	{
		document.all( scroll ).style.height = 0;
		document.all( scroll ).style.overflowY="hidden";
	}
}

/**
<jp> * [関数の説明]</jp>
<jp> * ListCellType3でheaderとdetailのテーブルの幅を同期させ、divの位置を修正します。</jp>
<jp> * [引数の説明]</jp>
<jp> * header 列固定ヘッダ テーブルID</jp>
<jp> * detail 列固定明細 テーブルID</jp>
<jp> * scrollHeader スクロールヘッダ テーブルID</jp>
<jp> * scrollDetail スクロール明細 テーブルID</jp>
<jp> * divLeftHeader 列固定ヘッダDIV ID</jp>
<jp> * divLeft 列固定明細DIV ID</jp>
<jp> * divHeader スクロールヘッダDIV ID</jp>
<jp> * divDetail スクロール明細DIV ID</jp>
<jp> * fixedBank 固定明細位置</jp>
<jp> * displayWidth DIVの幅</jp>
<jp> * displayHeight DIVの高さ</jp>
 */
function synchronizedScrollBarSizeScrollSide( header , detail , scrollHeader, scrollDetail ,
	divLeftHeader, divLeft ,divHeader , divDetail, fixedBank, displayWidth, displayHeight )
{
	try
	{
		var headerStatus = true;
		var detailStatus = true;
		var scrollHeaderStatus = true;
		var scrollDetailStatus = true;
		// 2010/10/04 IEのみの対応を解除
//		if( navigator.userAgent.indexOf("MSIE") == -1 )
//		{
//			retrun;
//		}

		// <jp>固定ヘッダの出力状態の確認を行います。</jp>
		if ( document.all( header ) == null ||
		     document.all( header ).style.visibility == 'hidden' ||
		     fixedBank <= 0 )
		{
			headerStatus = false ;
		}

		// <jp>スクロールヘッダの出力状態の確認を行います。</jp>
		if ( document.all( scrollHeader ) == null ||
		    document.all( scrollHeader ).style.visibility == 'hidden' )
		{
			scrollHeaderStatus = false ;
		}

		// <jp>固定明細の出力状態の確認を行います。</jp>
		if ( document.all( detail ) == null ||
		     document.all( detail ).style.visibility == 'hidden' )
		{
			detailStatus = false ;
		}

		if( ( document.all( detail ).rows.length == 0 ))//&& document.all( scrollDetail ).rows.length == 0 ) )
		{
			detailStatus = false ;
		}

		// <jp>スクロール明細の出力状態の確認を行います。</jp>
		if ( document.all( scrollDetail ) == null ||
		     document.all( scrollDetail ).style.visibility == 'hidden' )
		{
			scrollDetailStatus = false ;
		}
		if(document.all( scrollDetail ).rows.length == 0 )
		{
			scrollDetailStatus = false ;
		}
		else if(document.all( scrollDetail ).rows[0].childNodes.length == 0 )
		{
			scrollDetailStatus = false ;
		}

		// <jp>固定ヘッダ・明細が出力されている場合は同期処理を行います。</jp>
		if( headerStatus && detailStatus )
		{
			//<jp> 固定ヘッダ、明細の同期処理を行います。</jp>
			synchronizeDIVScroll( header, detail, divLeft, -1, -1 );
		}

		// <jp>スクロールヘッダ・明細が出力されている場合は同期処理を行います。</jp>
		if( scrollHeaderStatus && scrollDetailStatus )
		{
			//<jp> スクロールヘッダ、明細の同期処理を行います。</jp>
			synchronizeDIVScroll( scrollHeader, scrollDetail, divDetail, displayWidth, displayHeight );
		}

		// <jp>スクロールヘッダ、明細を出力しない場合はスクロールバーを非表示にします。</jp>
		if( !scrollHeaderStatus && !scrollDetailStatus )
		{
			document.all( divDetail ).style.overflow="hidden";
		}

		//<jp> 補正値を計算し、再度スクロールバーの表示位置を修正します。</jp>
		var scrollyFlg = false;
		var scrollxFlg = false;

		var divWidth= document.all( divDetail ).offsetWidth;
		var divHeight= document.all( divDetail ).offsetHeight;
		var scrollBarWidth = 0;
		var scrollBarHeight = 0;

		scrollBarWidth = document.all( divDetail ).offsetWidth - document.all( divDetail ).clientWidth;
		scrollBarHeight = document.all( divDetail ).offsetHeight - document.all( divDetail ).clientHeight;

		if( scrollDetailStatus )
		{
			//<jp> テーブルの幅よりDIVタグの幅が大きければ、テーブルの幅にあわせます。</jp>
			if(divWidth > document.all( scrollDetail ).offsetWidth)
			{
				//<jp> テーブルの高さよりDIVタグの高さが大きければ、スクロールバーを出さないため、スクロール幅をプラスしません。</jp>
				document.all( divHeader ).style.width = document.all( scrollDetail ).offsetWidth;

				//<jp> ヘッダDIVの幅を明細に合わせます。</jp>
				//<jp> テーブルの高さよりDIVタグの高さが大きければ、スクロールバーを出さないため、スクロール幅をプラスしません。</jp>
				if(divHeight < document.all( scrollDetail ).offsetHeight)
				{
					//<jp> 明細の幅とスクロールバーの位置の補正。</jp>
					document.all( divDetail ).style.width = (document.all( scrollDetail ).offsetWidth + scrollBarWidth);
				}

				//<jp> 横スクロールが表示されないため、スクロールバーの幅をマイナスします。</jp>
				document.all( divDetail ).style.height = document.all( divDetail ).offsetHeight - scrollBarHeight ;
				document.all( divDetail ).style.overflowX="hidden";
			}
			else
			{
				//<jp> テーブルの高さよりDIVタグの高さが大きければ、スクロールバーを出さないため、スクロール幅をプラスしません。</jp>
				if(divHeight > document.all( scrollDetail ).offsetHeight)
				{
					document.all( divDetail ).style.width = divWidth + "px";
				}

				// <jp>ヘッダの同期処理を行っていなければ、スクロールバーの同期処理を行います。</jp>
				if( !scrollHeaderStatus )
				{
					document.all( divDetail ).style.height = ( document.all( divDetail ).offsetHeight + scrollBarHeight );
				}
			}

			var nextDivHeight = ( document.all( scrollDetail ).offsetHeight > document.all( detail ).offsetHeight ) ? document.all( scrollDetail ).offsetHeight : document.all( detail ).offsetHeight;

			//<jp> テーブルの高さよりDIVタグの高さが大きければ、テーブルの高さにあわせます。</jp>
			if(divHeight > nextDivHeight )
			{
				document.all( divLeft ).style.height = nextDivHeight;
				document.all( divDetail ).style.height = ( document.all( scrollDetail ).offsetHeight + scrollBarHeight );
// add
				if(divWidth > document.all( scrollDetail ).offsetWidth)
				{
					document.all( divDetail ).style.height = ( document.all( scrollDetail ).offsetHeight );
				}
//end
				//<jp>スクロールバーを出さないのでDIVタグの幅よりスクロールバーの分マイナスする。</jp>
				document.all( divDetail ).style.width = ( document.all( divDetail ).offsetWidth - scrollBarWidth );
				document.all( divDetail ).style.overflowY="hidden";
			}
		}
		else
		{
			if( detailStatus )
			{
				// <jp>明細が表示されないので、スクロールバーを非表示にする</jp>
				document.all( divDetail ).style.overflowX="hidden";
				document.all( divDetail ).style.overflowY="hidden";

				// <jp>明細が表示されないので、スクロールDIVタグの高さ・幅をゼロにする</jp>
				document.all( divHeader ).style.height = 0;
				document.all( divHeader ).style.width  = 0;
				document.all( divDetail ).style.height = 0;
				document.all( divDetail ).style.width  = 0;

				if ( displayHeight > 0 && displayHeight < document.all( detail ).offsetHeight )
				{
					document.all( divLeft ).style.overflowY="scroll";
					var nextHeight = ( displayHeight > document.all( detail ).offsetHeight ) ? document.all( detail ).offsetHeight : displayHeight;

					document.all( divLeft ).style.height = nextHeight;
					document.all( divLeft ).style.overflowY = "scroll";

					document.all( divLeft ).style.width =
						document.all( divLeft ).offsetWidth + ( document.all( divLeft ).offsetWidth - document.all( divLeft ).clientWidth );
				}
			}
			else
			{
				// <jp>明細が表示されないので、スクロールバーを非表示にする</jp>
				document.all( divDetail ).style.overflowX="hidden";
				document.all( divDetail ).style.overflowY="hidden";

				// <jp>明細が表示されないので、DIVタグの高さをゼロにする</jp>
				document.all( divLeft ).style.height = 0;
				document.all( divDetail ).style.height = 0;

				if ( displayWidth > 0 && displayWidth < document.all( scrollHeader ).offsetWidth )
				{
					var nextWidth = ( displayWidth > document.all( scrollHeader ).offsetWidth ) ? document.all( scrollHeader ).offsetWidth : displayWidth;

					document.all( divHeader ).style.width = nextWidth;
					document.all( divHeader ).style.overflowX="scroll";
				}

			}
		}
	}
	catch( e )
	{
	}
}

/**
<jp> * [関数の説明]</jp>
<jp> * headerとdetailのテーブルの幅を同期させ、scrollの位置を修正します。(type3用)</jp>
<jp> * [引数の説明]</jp>
<jp> * header ヘッダテーブル</jp>
<jp> * detail 明細テーブル</jp>
<jp> * scroll スクロールバー用<div></jp>
<jp> * displayWidth DIVの幅</jp>
<jp> * displayHeight DIVの高さ</jp>
 */
function synchronizeDIVScroll( header, detail, scroll, displayWidth, displayHeight )
{
	try
	{
		//<jp> テーブル最大サイズの同期（小さいほうに合わせる）</jp>
		var headerTableWidth = document.all( header ).offsetWidth;
		var detailTableWidth = document.all( detail ).offsetWidth;

		//<jp> headerのwidthが大きい場合。</jp>
		if ( headerTableWidth > detailTableWidth )
		{
			//<jp> headerのwidthをdetailのwidthに合わせる。</jp>
			document.all( header ).style.width = detailTableWidth;
			document.all( detail ).style.width = detailTableWidth;
		}
		var dispW = ( displayWidth == -1 ) ? document.all( scroll ).offsetWidth : displayWidth;
		var dispH = ( displayHeight == -1 ) ? document.all( scroll ).offsetHeight : displayHeight;

		if ( parseInt( dispW ) > document.all( detail ).offsetWidth )
		{
			dispW = document.all( scroll ).offsetWidth;
		}
		if ( parseInt( dispH ) > document.all( detail ).offsetHeight )
		{
			dispH = document.all( scroll ).offsetHeight;
		}
		//<jp> スクロールバーの幅の取得</jp>
		//<jp> 物理サイズ - クライアントでの描画サイズ</jp>
		var scrollbarOffSetWidth = document.all( scroll ).offsetWidth - document.all( scroll ).clientWidth;
		//<jp> スクロールバーの高さの取得</jp>
		var scrollbarOffSetHeight = document.all( scroll ).offsetHeight - document.all( scroll ).clientHeight;
		//<jp> スクロール用DIVの幅の修正</jp>
		//<jp> detailのwidth + スクロールバーの幅</jp>
		document.all( scroll ).style.width = dispW + scrollbarOffSetWidth + "px";
		document.all( scroll ).style.height = dispH + scrollbarOffSetHeight + "px";
	}
	catch( e )
	{
	}
}


/**
<jp> * [関数の説明]</jp>
<jp> * ListCellType3でheaderとdetailのテーブルの幅を同期させ、divの位置を修正します。</jp>
<jp> * [引数の説明]</jp>
<jp> * header 列固定ヘッダ テーブルID</jp>
<jp> * detail 列固定明細 テーブルID</jp>
<jp> * scrollHeader スクロールヘッダ テーブルID</jp>
<jp> * scrollDetail スクロール明細 テーブルID</jp>
<jp> * divLeftHeader 列固定ヘッダDIV ID</jp>
<jp> * divLeft 列固定明細DIV ID</jp>
<jp> * divHeader スクロールヘッダDIV ID</jp>
<jp> * divDetail スクロール明細DIV ID</jp>
 */
function synchronizedScrollTable( header , detail , scrollHeader, scrollDetail ,divLeftHeader, divLeft ,divHeader , divDetail)
{
	try
	{
		var headerStatus = true;
		var detailStatus = true;
		var scrollHeaderStatus = true;
		var scrollDetailStatus = true;
		// 2010/10/04 IEのみの対応を解除
//		if( navigator.userAgent.indexOf("MSIE") == -1 )
//		{
//			retrun;
//		}

		// <jp>固定ヘッダの出力状態の確認を行います。</jp>
		if ( document.all( header ) == null ||
		     document.all( header ).style.visibility == 'hidden' )
		{
			headerStatus = false ;
		}

		// <jp>スクロールヘッダの出力状態の確認を行います。</jp>
		if ( document.all( scrollHeader ) == null ||
		    document.all( scrollHeader ).style.visibility == 'hidden' )
		{
			scrollHeaderStatus = false ;
		}

		// <jp>固定明細の出力状態の確認を行います。</jp>
		if ( document.all( detail ) == null ||
		     document.all( detail ).style.visibility == 'hidden' )
		{
			detailStatus = false ;
		}

		if( ( document.all( detail ).rows.length == 0 ))//&& document.all( scrollDetail ).rows.length == 0 ) )
		{
			detailStatus = false ;
		}

		// <jp>スクロール明細の出力状態の確認を行います。</jp>
		if ( document.all( scrollDetail ) == null ||
		     document.all( scrollDetail ).style.visibility == 'hidden' )
		{
			scrollDetailStatus = false ;
		}
		if(document.all( scrollDetail ).rows.length == 0 )
		{
			scrollDetailStatus = false ;
		}
		else if(document.all( scrollDetail ).rows[0].childNodes.length == 0 )
		{
			scrollDetailStatus = false ;
		}

		// <jp>固定ヘッダ・明細が出力されている場合は同期処理を行います。</jp>
		if( headerStatus && detailStatus )
		{
			//<jp> 固定ヘッダ、明細の同期処理を行います。</jp>
			synchronizedTableType3( header, detail, divLeft );
		}
		// <jp>スクロールヘッダ・明細が出力されている場合は同期処理を行います。</jp>
		if( scrollHeaderStatus && scrollDetailStatus )
		{
			//<jp> スクロールヘッダ、明細の同期処理を行います。</jp>
			synchronizedTableType3( scrollHeader, scrollDetail, divDetail );
		}

		// <jp>スクロールヘッダ、明細を出力しない場合はスクロールバーを非表示にします。</jp>
		if( !scrollHeaderStatus && !scrollDetailStatus  )
		{
			document.all( divDetail ).style.overflow="hidden";
		}

		//<jp> 補正値を計算し、再度スクロールバーの表示位置を修正します。</jp>
		var scrollyFlg = false;
		var scrollxFlg = false;

		var divWidth= document.all( divDetail ).offsetWidth;
		var divHeight= document.all( divDetail ).offsetHeight;
		var scrollBarWidth = 0;
		var scrollBarHeight = 0;

		scrollBarWidth = document.all( divDetail ).offsetWidth - document.all( divDetail ).clientWidth;
		scrollBarHeight = document.all( divDetail ).offsetHeight - document.all( divDetail ).clientHeight;

		if( scrollDetailStatus )
		{
			//<jp> テーブルの幅よりDIVタグの幅が大きければ、テーブルの幅にあわせます。</jp>
			if(divWidth > document.all( scrollDetail ).offsetWidth)
			{
				//<jp> テーブルの高さよりDIVタグの高さが大きければ、スクロールバーを出さないため、スクロール幅をプラスしません。</jp>
				document.all( divHeader ).style.width = document.all( scrollDetail ).offsetWidth;

				//<jp> ヘッダDIVの幅を明細に合わせます。</jp>
				//<jp> テーブルの高さよりDIVタグの高さが大きければ、スクロールバーを出さないため、スクロール幅をプラスしません。</jp>
				if(divHeight < document.all( scrollDetail ).offsetHeight)
				{
					//<jp> 明細の幅とスクロールバーの位置の補正。</jp>
					document.all( divDetail ).style.width = (document.all( scrollDetail ).offsetWidth + scrollBarWidth);
				}

				//<jp> 横スクロールが表示されないため、スクロールバーの幅をマイナスします。</jp>
				document.all( divDetail ).style.height = document.all( divDetail ).offsetHeight - scrollBarHeight ;
				document.all( divDetail ).style.overflowX="hidden";
			}
			else
			{
				//<jp> テーブルの高さよりDIVタグの高さが大きければ、スクロールバーを出さないため、スクロール幅をプラスしません。</jp>
				if(divHeight > document.all( scrollDetail ).offsetHeight)
				{
					document.all( divDetail ).style.width = divWidth + "px";
				}

				// <jp>ヘッダの同期処理を行っていなければ、スクロールバーの同期処理を行います。</jp>
				if( !scrollHeaderStatus )
				{
					document.all( divDetail ).style.height = ( document.all( divDetail ).offsetHeight + scrollBarHeight );
				}
			}

			var nextDivHeight = ( document.all( scrollDetail ).offsetHeight > document.all( detail ).offsetHeight ) ? document.all( scrollDetail ).offsetHeight : document.all( detail ).offsetHeight;

			//<jp> テーブルの高さよりDIVタグの高さが大きければ、テーブルの高さにあわせます。</jp>
			if(divHeight > nextDivHeight )
			{
				document.all( divLeft ).style.height = nextDivHeight;
				document.all( divDetail ).style.height =( document.all( scrollDetail ).offsetHeight + scrollBarHeight );

				//<jp>スクロールバーを出さないのでDIVタグの幅よりスクロールバーの分マイナスする。</jp>
				document.all( divDetail ).style.width = ( document.all( divDetail ).offsetWidth - scrollBarWidth );
				document.all( divDetail ).style.overflowY="hidden";
			}
		}
		else
		{
			if( detailStatus )
			{
				// <jp>明細が表示されないので、スクロールバーを非表示にする</jp>
				document.all( divDetail ).style.overflowX="hidden";
				document.all( divDetail ).style.overflowY="hidden";

				// <jp>明細が表示されないので、スクロールDIVタグの高さ・幅をゼロにする</jp>
				document.all( divHeader ).style.height = 0;
				document.all( divHeader ).style.width  = 0;
				document.all( divDetail ).style.height = 0;
				document.all( divDetail ).style.width  = 0;

			}
			else
			{
				// <jp>明細が表示されないので、スクロールバーを非表示にする</jp>
				document.all( divDetail ).style.overflowX="hidden";
				document.all( divDetail ).style.overflowY="hidden";

				// <jp>明細が表示されないので、DIVタグの高さをゼロにする</jp>
				document.all( divLeft ).style.height = 0;
				document.all( divDetail ).style.height = 0;

				// <jp>DIVのサイズによりヘッダが隠れてしまうため、ヘッダDIVにはTABLEタグのサイズを適用する。</jp>
				document.all( divHeader ).style.width = document.all( scrollHeader ).offsetWidth;
			}
		}
	}
	catch( e )
	{
	}
}


/**
<jp> * [関数の説明]</jp>
<jp> * headerとdetailのテーブルの幅を同期させ、scrollの位置を修正します。(type3用)</jp>
<jp> * [引数の説明]</jp>
<jp> * header ヘッダテーブル</jp>
<jp> * detail 明細テーブル</jp>
<jp> * scroll スクロールバー用<div></jp>
 */
function synchronizedTableType3( header, detail, scroll )
{
	try
	{
		//<jp> 同期処理の開始</jp>
		var headerCells = document.all( header ).rows[0].cells;
		var detailCells = document.all( detail ).rows[0].cells;
		var maxWidth = 0;

		//<jp> セルの幅を一つずつ比較してWidthを補正していきます。</jp>
		for ( var i = headerCells.length-1; i >= 0; i-- )
		{
			var headerWidth = headerCells[i].offsetWidth;
			var detailWidth = detailCells[i].offsetWidth;

			var headerPadding = distillNumber(headerCells[i].currentStyle.paddingRight) + distillNumber(headerCells[i].currentStyle.paddingLeft);
			var detailPadding = distillNumber(detailCells[i].currentStyle.paddingRight) + distillNumber(detailCells[i].currentStyle.paddingLeft);

			headerWidth += headerPadding;
			detailWidth += detailPadding;

			//<jp> 同じ場合は次のセルへ</jp>
			if ( headerWidth  == detailWidth )
			{
				maxWidth = headerWidth;
			}

			var isHeaderWidth = false;
			//<jp> headerのwidthが大きい場合。</jp>
			if ( headerWidth > detailWidth )
			{
				maxWidth = headerWidth;
				isHeaderWidth = true;
			}
			//<jp> detailのwidthが大きい場合。</jp>
			else
			{
				maxWidth = detailWidth;
			}

			//<jp> 補正値の割り当て</jp>
			document.all( header ).rows[0].cells[i].width = maxWidth;
			document.all( detail ).rows[0].cells[i].width = maxWidth;

//			if ( isHeaderWidth )
//			{
				for ( var j = 0; j < document.all( detail ).rows.length; j++ )
				{
					document.all( detail ).rows[j].cells[i].width = maxWidth;
				}
//			}
		}

		//<jp> テーブル最大サイズの同期（小さいほうに合わせる）</jp>
		var headerTableWidth = document.all( header ).offsetWidth;
		var detailTableWidth = document.all( detail ).offsetWidth;

		//<jp> headerのwidthが大きい場合。</jp>
		if ( headerTableWidth > detailTableWidth )
		{
			//<jp> headerのwidthをdetailのwidthに合わせる。</jp>
			document.all( header ).style.width = detailTableWidth;
			document.all( detail ).style.width = detailTableWidth;
		}
		//<jp> detailのwidthが大きい場合。</jp>
		else if ( headerTableWidth < detailTableWidth )
		{
			//<jp> detailのwidthをheaderのwidthに合わせる。</jp>
			document.all( detail ).style.width = headerTableWidth;
			document.all( header ).style.width = headerTableWidth;
		}

		//<jp> スクロールバーの幅の取得</jp>
		//<jp> 物理サイズ - クライアントでの描画サイズ</jp>
		var scrollbarOffSetWidth = document.all( scroll ).offsetWidth - document.all( scroll ).clientWidth;
		//<jp> スクロールバーの高さの取得</jp>
		var scrollbarOffSetHeight = document.all( scroll ).offsetHeight - document.all( scroll ).clientHeight;

		//<jp> スクロール用DIVの幅の修正</jp>
		//<jp> detailのwidth + スクロールバーの幅</jp>
		document.all( scroll ).style.width = document.all( scroll ).offsetWidth + scrollbarOffSetWidth + "px";
		document.all( scroll ).style.height = document.all( scroll ).offsetHeight + scrollbarOffSetHeight + "px";
	}
	catch( e )
	{
	}
}

/**
<jp> * [関数の説明]</jp>
<jp> * 数値の単位などの場合、数字以外の文字が登場するまでの数値を返します。</jp>
<jp> * [引数の説明]</jp>
<jp> * value 数値＋文字列 </jp>
<jp> * [戻り]</jp>
<jp> * 数値のみ</jp>
 */
function distillNumber( value )
{
	var ret = "";
	for( var i = 0; i < value.length; i++ )
	{
		if( value.charAt(i).match(/[0-9]/g ) )
		{
			ret += value.charAt(i);
		}
		else
		{
			break;
		}
	}
	if ( ret.length == 0 )
	{
		ret = 0;
	}
	return parseInt( ret );
}

/**
<jp> * [関数の説明]</jp>
<jp> * 伸縮リストセルの展開、縮小処理。</jp>
 *
<jp> * [引数の説明]</jp>
<jp> * tableId テーブルId</jp>
<jp> * divId DIVId</jp>
<jp> * fixedBank 表示位置</jp>
<jp> * colSize 列の数(列結合をしていない状態)</jp>
 */
function expandTable( tableId, divId, fixedBank, colSize)
{
	var divOffSet = 16;
	var table = document.all( tableId );
	var div = document.all( divId );
	var isExpand = document.all( tableId + "__EXPAND" ).value;

	if ( table == null || table.style.visibility == 'hidden' || fixedBank <= 0 )
	{
		return;
	}
	if ( table.rows == null || table.rows.length <= 0 )
	{
		return;
	}
	// colタグの取得
	var colTags = table.getElementsByTagName("col");
	if (colTags == null)
	{
		return;
	}

// fixedBankの位置補正
	fixedBank = fixedBank - colSize + colTags.length;

//	var headerCells = table.rows[0].cells;
//	if ( isExpand == "true" ||  fixedBank >= headerCells.length )
//	if ( isExpand == "true" ||  fixedBank > headerCells.length )
	if ( isExpand == "true")
	{
		for (var i = 0; i < colTags.length; i++)
		{
			colTags[i].style.width = colTags[i].width;
		}
		document.all( tableId + "__right" ).style.visibility = "visible";
		document.all( tableId + "__EXPAND" ).value = "false";
		document.all( tableId + "__left" ).src = contextPath + "/img/control/listcell/1_1.gif";
		expandVisibility( table, fixedBank, "block" );
	}
	else
	{
		for (var i = 0; i < colTags.length; i++)
		{
			if (i >= fixedBank)
			{
				colTags[i].style.width = "0px";
			}
		}
		document.all( tableId + "__right" ).style.visibility = "hidden";
		document.all( tableId + "__EXPAND" ).value = "true";
		document.all( tableId + "__left" ).src = contextPath + "/img/control/listcell/1_2.gif";
		expandVisibility( table, fixedBank, "none" );
	}
	if ( div.offsetHeight > table.offsetHeight )
	{
		div.style.height = table.offsetHeight;
	}
}


/**
<jp> * [関数の説明]</jp>
<jp> * 伸縮リストセルの非表示化</jp>
 *
<jp> * [引数の説明]</jp>
<jp> * table テーブル</jp>
<jp> * fixedBank 表示位置</jp>
<jp> * visible 表示</jp>
 */
function expandVisibility( table, fixedBank, visible )
{
	var rowLength = table.rows.length;
	var rowsize = 0;
	var trId = "___";

	// １レコード分の行数の取得
	for(var i=0; i<rowLength; i++)
	{
		var cell = table.rows[i].cells[0];
		if(i==0)
		{
			trId = cell.parentNode.id;
		}
		else if(trId != cell.parentNode.id)
		{
			rowsize = i;
			break;
		}
	}

	if(rowsize == 0)
	{
		rowsize = rowLength;
	}
	/** １レコードの rowSpan によるズレの補正用配列 */
	var ajastArray = new Array(rowsize);

	/** １レコードの各行に対する実際の FixedBank */
	var limitArray = new Array(rowsize);

	// 配列の初期化
	for(var i=0; i<rowsize; i++)
	{
		ajastArray[i] = 0;
		limitArray[i] = 0;
	}

	// １レコードの各行に対する実際の FixedBank の計算
	for(var i=0; i<rowsize; i++)
	{
		/** colSpan, rowSpan を含めた実際の列位置 */
		var realColCount = ajastArray[i];

		var colLength = table.rows[i].cells.length;
		limitArray[i] = colLength;
		for(var j=0; j<colLength; j++)
		{
			var cell = table.rows[i].cells[j];

			// colSpan の補正
			realColCount += cell.colSpan;
			for(var k=1; k<cell.rowSpan; k++)
			{
				// rowSpan の分以降の行を補正する。
				ajastArray[k+i] += cell.colSpan;
			}

			// FixedBank を超えたら境界とする。
			if(realColCount >= fixedBank)
			{
				limitArray[i] = j;
				break;
			}
		}
	}

	// 表示・非表示の書換え
	for ( var i = 0; i < rowLength; i++ )
	{
		var colLength = table.rows[i].cells.length;
		var index = (i==0) ? 0 : i%rowsize;
		for(var j = limitArray [index] + 1; j<colLength; j++)
		{
			var cellStyle = table.rows[i].cells[j].style;
			// 表示に差分があるときのみ書換え
			if( cellStyle.display != visible)
			{
				cellStyle.display = visible;
			}
		}
	}
	return;
}

/**
<jp> * [関数の説明]</jp>
<jp> * リストセルType3の固定明細とスクロール明細のテーブルの高さを同期させます。</jp>
<jp> * [引数の説明]</jp>
<jp> * leftDetail 固定明細テーブル</jp>
<jp> * rightDetail スクロール明細テーブル</jp>
 */
function synchronizedTableDetail( leftDetail, rightDetail )
{
	try
	{
		// 2010/10/04 IEのみの対応を解除
//		if( navigator.userAgent.indexOf("MSIE") == -1 )
//		{
//			return;
//		}

		var leftBody = document.all( leftDetail );
		var rightBody = document.all( rightDetail );
		var array = new Array();

		for ( var i = 0 ; i < rightBody.rows.length ; i++ )
		{
			var maxHeight = 0;
			var leftDetailHeight = 0;
			var rightDetailHeight = 0;

			// <jp>固定明細の最大の高さを取得します。結合されているセルについてはチェックを行いません。</jp>
			for ( var j = 0 ; j < leftBody.rows[ i ].childNodes.length ; j ++ )
			{
				var tmpHeight = leftBody.rows[ i ].childNodes[ j ].offsetHeight;
				var span = leftBody.rows[ i ].childNodes[ j ].rowSpan ;
				if ( leftDetailHeight < tmpHeight
					&& span == 1 )
				{
					leftDetailHeight = tmpHeight;
				}
				// <jp>結合されているセルである場合</jp>
				if ( span > 1 )
				{
					// <jp>セルの位置と、結合数を取得します。</jp>
					array[array.length] = new Array( i , j , span , true);
				}
			}

			// <jp>スクロール明細の最大の高さを取得します。結合されているセルについてはチェックを行いません。</jp>
			for ( var j = 0 ; j < rightBody.rows[ i ].childNodes.length ; j ++ )
			{
				var tmpHeight = rightBody.rows[ i ].childNodes[ j ].offsetHeight;
				var span = rightBody.rows[ i ].childNodes[ j ].rowSpan ;
				if ( rightDetailHeight < tmpHeight
					&& rightBody.rows[ i ].childNodes[ j ].rowSpan == 1 )
				{
					rightDetailHeight = tmpHeight;
				}
				// <jp>結合されているセルである場合</jp>
				if ( span > 1 )
				{
					array[array.length] = new Array( i , j , span , false );
				}
			}

			// <jp>固定のheightが明細より大きい場合。</jp>
			if ( leftDetailHeight > rightDetailHeight )
			{
				for ( var j = 0 ; j < rightBody.rows[ i ].childNodes.length ; j ++ )
				{
					if( rightBody.rows[ i ].childNodes[ j ].rowSpan == 1 )
					{
						rightBody.rows[ i ].cells[ j ].height = leftDetailHeight;
						// <jp>最大の高さのセルがあれば、他のセルはそれにあわすため後続処理を行わない</jp>
						break;
					}
				}

				for ( var j = 0 ; j < leftBody.rows[ i ].childNodes.length ; j ++ )
				{
					if( leftBody.rows[ i ].childNodes[ j ].rowSpan == 1 )
					{
						leftBody.rows[ i ].cells[ j ].height = leftDetailHeight;
					}
				}
			}
			// <jp>明細のheightが固定より大きい場合。</jp>
			else
			{
				for ( var j = 0 ; j < leftBody.rows[ i ].childNodes.length ; j ++ )
				{
					if( leftBody.rows[ i ].childNodes[ j ].rowSpan == 1 )
					{
						leftBody.rows[ i ].cells[ j ].height = rightDetailHeight;
						// <jp>最大の高さのセルがあれば、他のセルはそれにあわすため後続処理を行わない</jp>
						break;
					}
				}

				for ( var j = 0 ; j < rightBody.rows[ i ].childNodes.length ; j ++ )
				{
					if( rightBody.rows[ i ].childNodes[ j ].rowSpan == 1 )
					{
						rightBody.rows[ i ].cells[ j ].height = rightDetailHeight;
					}
				}
			}
		}

		// <jp>結合されているセルがある場合は、全ての補正が終了後に再度補正を行います。</jp>
		if( array.length != 0 )
		{
			// <jp>結合されているセルを順番に参照します。</jp>
			for ( var i =( array.length -1 ); i >= 0 ; i-- )
			{
				var totalHeight = 0;
				var heightChange = false;

				// <jp>一行の最大値を取得します。左側のテーブルが全結合している場合のみ右側のテーブルを採用します。</jp>
				var max = 0;
				max = array[i][2] + array[i][0];


				// <jp>該当する行の合計の高さを計算します。</jp>
				// <jp>結合している行数ぶん繰り返します。</jp>
				for ( var j = array[i][0] ; j < max ; j++ )
				{
					// <jp>１行の中で、結合していないセルの高さを取得します。</jp>
					for ( var k = 0 ; k < leftBody.rows[ j ].childNodes.length ; k++ )
					{
						if( leftBody.rows[ j ].cells[ k ] != undefined )
						{
							if( leftBody.rows[ j ].cells[ k ].rowSpan <= 1 )
							{
								totalHeight += leftBody.rows[ j ].childNodes[ k ].offsetHeight;
								heightChange = true;
								break;
							}
						}
					}

					if( !heightChange )
					{
						// <jp>１行の中で、結合していないセルの高さを取得します。</jp>
						for ( var k = 0 ; k < rightBody.rows[ j ].childNodes.length ; k++ )
						{
							if( rightBody.rows[ j ].cells[ k ].rowSpan <= 1 )
							{
								totalHeight += rightBody.rows[ j ].cells[ k ].offsetHeight;
								break;
							}
						}
					}
				}

				if ( array[i][3] )
				{
					var nowHeight = leftBody.rows[ array[i][0] ].cells[ array[i][1] ].offsetHeight;

					if(totalHeight != nowHeight )
					{
						// <jp>結合しているセルが左側（固定明細）の場合</jp>
						leftBody.rows[ array[i][0] ].cells[ array[i][1] ].height = totalHeight;
					}
				}
				else
				{
					var nowHeight = rightBody.rows[ array[i][0] ].cells[ array[i][1] ].offsetHeight;

					if(totalHeight != nowHeight )
					{
						// <jp>結合しているセルが右側（スクロール明細）の場合</jp>
						rightBody.rows[ array[i][0] ].cells[ array[i][1] ].height = totalHeight;
					}
				}
			}
		}
	}
	catch( e )
	{
	}
}

/** <jp>
 * [関数の説明]
 * detailとscrollの表示状態の同期を取ります。
 *
 * [引数の説明]
 * detail 明細テーブル
 * scroll 水平スクロールバー用<div>
</jp> */
function synchronizedHorizonScrollBar( detail, scroll )
{
	try
	{
		if ( document.all( detail ) == null ||
		     document.all( detail ).style.visibility == 'hidden' )
		{
			if ( document.all( scroll ) != null )
			{
				document.all( scroll ).style.visibility = 'hidden';
			}
			return;
		}
	}
	catch(e)
	{
	}
}

var w3c = (document.getElementById) ? true : false;
var ie = ( document.all ) ? true : false;
var N = -1;
var bars = new Array();

/**
<jp> * [関数の説明]</jp>
<jp> * プログレスバーを描画します。</jp>
<jp> * [引数の説明]</jp>
<jp> * width プログレスバーのwidth</jp>
<jp> * heiht プログレスバーのheigh</jp>
<jp> * bgcolor プログレスバーのbgcolor</jp>
<jp> * brdwidth プロブレスバーのボーダーのwidth</jp>
<jp> * brdcolor プロブレスバーのボーダーのcolor</jp>
<jp> * blkcolor ブロックのcolor</jp>
<jp> * speed ブロックの描画速度</jp>
<jp> * blocks ブロック数</jp>
<jp> * id innerHTMLで描画するコントロールのid</jp>
 */
function createBar( width, height, bgcolor, brdwidth, brdcolor, blkcolor, speed, blocks, id )
{
	if( ie || w3c )
	{
		//<jp> プログレスバーのバックグランドの設定</jp>
		var bar='<div style="position:relative; overflow:hidden; width:' + width + 'px; ' +
				'height:' + height + 'px; ' +
				'background-color:' + bgcolor + "; " +
				'border-color:' + brdcolor + '; ' +
				'border-width:' + brdwidth + 'px; ' +
				'border-style:solid; font-size:1px;">';

		//<jp> プロブレスバーのブロックの設定</jp>
		bar += '<span id = "blocks' + ( ++N ) + '" style="left:-' + ( height * 2 + 1 ) + 'px; position:absolute; font-size:1px">';

		//<jp> ブロックを引数で指定された数だけ設定します。</jp>
		for( i = 0; i < blocks; i++ )
		{
			//<jp> ブロック用タグの作成</jp>
			bar+='<span style="background-color:' + blkcolor + '; ' +
				 'left:-' + ( ( height * i ) + i ) + 'px; '+
				 'font-size:1px; position:absolute; ' +
				 'width:' + height + 'px; ' +
				 'height:' + height +'px; ';

			//<jp> IEである場合</jp>
			if ( ie )
			{
				bar += 'filter:alpha( opacity=' + ( 100 - i * ( 100 / blocks ) ) + ')';
			}
			else
			{
				bar += '-Moz-opacity:' + ( ( 100 - i * ( 100 / blocks ) ) /100 );
			}
			bar += '"></span>';
		}

		bar += '</span></div>';

		//<jp> プログレスバーの描画</jp>
		document.all( id ).innerHTML = bar;

		//<jp> ブロックの取得</jp>
		var bA = null;

		//<jp> IEである場合</jp>
		if ( ie )
		{
			bA = document.all[ 'blocks' + N ];
		}
		else
		{
			bA = document.getElementById( 'blocks' + N );
		}

		bA.blocks = blocks;
		bA.width = width;
		bA.height = height;
		bars[ bars.length ] = bA;
		setInterval( 'startBar(' + N +')', speed );
	}
}


/**
<jp> * [関数の説明]</jp>
<jp> * ブロック描画を補正します。</jp>
<jp> * [引数の説明]</jp>
<jp> * bn ブロックNo.</jp>
 */
function startBar( bn )
{
	var bar = bars[ bn ];
	var position = -1;

	if ( parseInt( bar.style.left ) + bar.height + 1 - ( bar.blocks * bar.height + bar.blocks ) > bar.width )
	{
		position = -( bar.height * 2 + 1 );
	}
	else
	{
		position = ( parseInt( bar.style.left ) + bar.height + 1 );
	}
	bar.style.left = position  + 'px';
}


/**
<jp> * [関数の説明]</jp>
<jp> * 1ミリ秒毎にcheckOpenerFlg()を実行します。</jp>
 */
function timer()
{
  myTim = setInterval("checkOpenerFlg()",100);
}


/**
<jp> * [関数の説明]</jp>
<jp> * openerのisOnloadフラグを確認し、</jp>
<jp> * falseの場合は自画面を閉じます。</jp>
 */
function checkOpenerFlg()
{
  try
  {
    if(window.opener.closed || !window.opener.isOnload)
    {
       this.close();
    }
    window.focus();
  }
  catch(e)
  {
  }
}


/**
<jp> * [関数の説明]</jp>
<jp> * プログレスバーにメッセージを表示します。</jp>
 */
function setProgressMessage()
{
  try
  {
    if(window.opener.__PROGRESS_MESSAGE != null && window.opener.__PROGRESS_MESSAGE != undefined)
    {
      document.all("msg").innerText = window.opener.__PROGRESS_MESSAGE;
    }
    else
    {
      document.all("msg").innerText = "";
    }
  }
  catch(e)
  {
    document.all("msg").innerText = "";
  }
}


/** <jp>
 * [関数の説明]
 * 文字列の置換を行います
 * [使用するカスタムタグ名]
 * NumberTextBoxTag
 * [引数の説明]
 * val     データ
 * oldval  置換対象文字列
 * newval  置換文字列
 * [戻り]
 * 置換された文字列
 </jp> */
function replaceAll(val, oldval, newval) {

    if (val==null ||val.length==0 || oldval==null || oldval.length==0 || newval==null)
    {
        return val;
    }
    var sub1 = "";
    var sub2 = val;

    while (true) { //<jp> break条件に入るまでループ。</jp>
        index = sub2.indexOf(oldval, 0);

        if (index == -1) {
            break;
        }

        val = sub2.replace(oldval, newval);

        sub1 += val.substring(0, index + newval.length);
        sub2 =  val.substring(index + newval.length, val.length);

    }
    return sub1 + sub2;
}

/** <jp>
 * [関数の説明]
 * プログレスウィンドウを中央表示させます。
 * [引数の説明]
 * progressWindow     中央表示させるウィンドウオブジェクト
 </jp> */
function progressMove( progressWindow )
{
    try
    {
        var windowWidth = progressWindow.document.body.clientWidth;
        var windowHeight = progressWindow.document.body.clientHeight;
        var screenWidth = screen.availWidth;
        var screenHeight = screen.availHeight;
        var centerPointWidth = (screenWidth - windowWidth) / 2;
        var centerPointHeight = (screenHeight - windowHeight) /2;

        progressWindow.moveTo(centerPointWidth, centerPointHeight);
    }
    catch(e)
    {
    }
}

/** <jp>
 * [関数の説明]
 * プログレスウィンドウの初期化処理を行います。
 * [引数の説明]
 * obj     中央表示させるウィンドウオブジェクト
 </jp> */
function progressInit(obj)
{
    setProgressMessage();
    progressMove(obj);
    timer();
}

//Add 2007/09/19 v4.2.3
/** <jp>
 * [関数の説明]
 * フォーマットチェックを行います。
 * [使用するカスタムタグ名]
 * FormatTextBoxBoxTag
 * [引数の説明]
 * str     データ
 * format  FormatTextBoxに定義されているフォーマット
 * pause   フォーマットを行う文字
 * [戻り]
 * 指定されてるフォーマットと一致しないならfalse、一致するならtrueを返します。
 </jp> */
function isFormatCheck(str,format,pause)
{
	if( str == "" ) return true;

	do
	{
		format = format.replace( pause, "" );
	}while( format.indexOf( pause )!=-1 );

	if( str.length!=format.length ) return false;

	for( var i=0 ; i<format.length ; i++ )
	{
		var strChar = str.charAt(i);
 		var formatChar = format.charAt(i);

		if( formatChar=="9" )
		{
			if( strChar.match(/[^0-9]/) ) return false;
		}
		else if( formatChar=="X" )
		{
			if( strChar.match(/[^0-9A-Z]/) ) return false;
		}
		// Add 2008/05/13 v4.3.1.0
		else if( formatChar=="x" )
		{
			if( strChar.match(/[^0-9a-z]/) ) return false;
		}
		else if( formatChar=="*" )
		{
			if( strChar.match(/[^0-9A-Za-z]/) ) return false;
		}
		// End 2008/05/13 v4.3.1.0
		// Add Start 2008/07/10 v4.3.2.0 T.Ogawa
		else
		{
			return false;
		}
		// Add End 2008/07/10 v4.3.2.0 T.Ogawa
	}

	return true;
}
//End 2007/09/19 v4.2.3

//Add 2007/11/09 v4.2.4

/** <jp>
 * [関数の説明]
 * データが指定されたバイト数を超えていないかチェックします。
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * str  データ
 * byte バイト数
 * [戻り]
 * 指定されたバイト数を超えていたら false、超えていなければtrueを返します。
 </jp> */
function isByteCheckNoRTrim(str,bytes)
{
	if( str == "" ) return true;
	if( bytes == "" ) return true;

	if( getByteNoRTrim( str ) > bytes ) {
		return false;
	} else {
		return true;
	}
}

/** <jp>
 * [関数の説明]
 * データのバイト数を取得します。
 * 全角文字は2byte、半角カナの場合は1byteとして計算します。
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * str データ
 * [戻り]
 * バイト数を返します。
 </jp> */
function getByteNoRTrim( str )
{
	var i, cnt =0;
	var CLetter;

	for (i=0; i<str.length; i++) {

		CLetter = str.charAt(i);

		if (CLetter.match(ptn)) {
			cnt++;
    	}else{
			cnt += 2;
    	}
	}
	return cnt;
}

//End 2007/11/09 v4.2.4

//Add 2007/10/01 v4.3
/** <jp>
 * 現在フォーカスが当たっているエレメントを保持します。
 </jp> */
var focusElement = null;

/** <jp>
 * [関数の説明]
 * 現在フォーカスが当たっているエレメントを保存します。
 * [引数の説明]
 * ele     現在フォーカスが当たっているエレメント
 </jp> */
function setThisFocus(ele)
{
	if( ele.id!=null && ele.id!="" )
	{
		focusElement = ele;
	}
	else
	{
		focusElement = null;
	}
}

/** <jp>
 * [関数の説明]
 * setThisFocusで保持したエレメントにフォーカスを当てます。
 </jp> */
function prevFocus()
{
	if(focusElement != null)
	{
		document.all(focusElement.id).focus();
		focusElement = null;
	}
}
//End 2007/10/01 v4.3

// added by Muni on 2008/04/15 .. BusiTune Request
/** <en>
 * Method to download files like xls , cvs etc
 * @param Absolute file path
 </en> */
function downlaod(filePath)
{
	try
	{
		/*ifrm = document.createElement("IFRAME");
		ifrm.src=filePath;
		ifrm.style.display = "none";
		document.body.appendChild(ifrm);*/
        window.location.href = filePath;

	}catch(e)
	{
		alert(e.toString());
	}
}

/** <en>
 * Method to preview PDF files
 </en> */
function previewPDF(pdfPath,width,height)
{
	try
	{
        size = 'width=' + width + ',height=' + height + ',resizable=yes';
        // modify 2009/12/15 ver5.1.0.1 T.Ogawa start
        // ウィンドウ名を毎回採番し複数ウィンドウをオープン可能に変更
        var windowName = 'PDF' + new Date().getTime();

        dialogWindow = window.open(pdfPath, windowName, size);
        // modify 2009/12/15 ver5.1.0.1 T.Ogawa end

        addWindowObject( dialogWindow );
        if ( !isClosed( dialogWindow ) )
        {
           moveToCenter( dialogWindow );
           dialogWindow.focus();
        }

	}catch(e)
	{
		alert(e.toString());
	}
}
//Muni end

/*2011/12/29 LinButtonからpdfにリンクされた場合は、previewPDFを使用するよう修正 */
function linkToPDF(args)
{
	var path = args[0];
	var name =args[1];
	var option = args[2];

	try
	{
		var windowSizeDefinedLocationNotDefined = option.indexOf("width") != -1 && option.indexOf("height") != -1 && option.indexOf("top") == -1 && option.indexOf("left") == -1;
		if (windowSizeDefinedLocationNotDefined) {
			// pdfのウィンドウはwindowオブジェクトのプロパティにアクセスするとエラーが発生する場合があるので
			// width、heightが指定されており、top、leftが指定されていない場合は計算でtop、leftを求めてからwindow.openを呼び出す
			var ops = option.split(",");
			var top = 0;
			var left = 0;
			var width = 0;
			var height = 0;
			for (var i=0; i<ops.length; i++) {
				var ops2 = ops[i].split("=");
				if (ops2.length > 1) {
					if (ops2[0].toLowerCase() == "width") {
						try {width = parseInt(ops2[1]);} catch (e) {width = 0;}
					}
					else if (ops2[0].toLowerCase() == "height") {
						try {height = parseInt(ops2[1]);} catch (e) {height = 0;}
					}
				}
			}
			if (width != 0 && height != 0) {
				top = (screen.availHeight - height) / 2;
				left = (screen.availWidth - width) / 2;
				var _dialogWindow = window.open(path, name, option + ",top=" + top + ",left=" + left);
			    if ( !isClosed( _dialogWindow ) )
			    {
			       _dialogWindow.focus();
			    }
			}
		}
		else
		{
		    var _dialogWindow = window.open(path, name, option);
		    if ( !isClosed( _dialogWindow ) )
		    {
		       moveToCenter( _dialogWindow );
		       _dialogWindow.focus();
		    }
		}
	}catch(e)
	{
		alert(e.toString());
	}
	clearSendFlagTimer();
}


function synchronizedScrollBarSizeScrollSideForMultirole( id, top, bottom, header , detail , scrollHeader, scrollDetail ,
		divLeftHeader, divLeft ,divHeader , divDetail, fixedBank, displayWidth, displayHeight, rowSize, colSize ,visible)
{
	/* 全体を包括するdiv */
	var mainDiv = document.getElementById(id);

	/* 上divと下div */
	var topDiv = document.getElementById(top);
	var bottomDiv = document.getElementById(bottom);

	/* 左上divとtable */
	var leftTopDiv = document.getElementById(divLeftHeader);
	var leftTopTable = document.getElementById(header);
	/* 右上divとtable */
	var rightTopDiv = document.getElementById(divHeader);
	var rightTopTable = document.getElementById(scrollHeader);
	/* 左下divとtable */
	var leftBottomDiv = document.getElementById(divLeft);
	var leftBottomTable = document.getElementById(detail);
	/* 右下divとtable */
	var rightBottomDiv = document.getElementById(divDetail);
	var rightBottomTable = document.getElementById(scrollDetail);

	var rightColTags = rightTopTable.getElementsByTagName("col");
	var leftColTags = leftTopTable.getElementsByTagName("col");


	/* 各部分のtableの存在有無 */
	var headerStatus = true;
	var detailStatus = true;
	var scrollHeaderStatus = true;
	var scrollDetailStatus = true;
	// 2010/10/04 IEのみの対応を解除
//	if( navigator.userAgent.indexOf("MSIE") == -1 )
//	{
//		retrun;
//	}

	/* ここから、各tableの存在有無確認 */
	// <jp>固定ヘッダ(左上)の出力状態の確認を行います。</jp>
	if (leftTopTable == null ||
			topDiv.style.visibility =='hidden' || leftTopTable.style.visibility == 'hidden' || fixedBank <= 0 || colSize == fixedBank)
	{
		headerStatus = false ;
	}

	// <jp>スクロールヘッダ(右上)の出力状態の確認を行います。</jp>
	if ( rightTopTable == null ||
			topDiv.style.visibility =='hidden' || rightTopTable.style.visibility == 'hidden')
	{
		scrollHeaderStatus = false ;
	}

	// <jp>固定明細(左下)の出力状態の確認を行います。</jp>
	if ( leftBottomTable == null || leftBottomTable.style.visibility == 'hidden' || leftBottomTable.rows.length == 0 || fixedBank <= 0  )
	{
		detailStatus = false ;
	}

	// <jp>スクロール明細(右下)の出力状態の確認を行います。</jp>
	if ( rightBottomTable == null || rightBottomTable.style.visibility == 'hidden' || rightBottomTable.rows.length == 0)
	{
		scrollDetailStatus = false ;
	}
	else if(rightBottomTable.rows[0].childNodes.length == 0 )
	{
		scrollDetailStatus = false ;
	}

	// 2013/05/20 v5.2.2.23 start
	var headerHidden = !headerStatus && !scrollHeaderStatus;
	// 2013/05/20 v5.2.2.23 end
	
	/* ここまで、各tableの存在有無確認 */
	// 初期値の幅＆高さを指定
	var LEFT_WIDTH = leftTopDiv.offsetWidth > leftTopTable.offsetWidth ? leftTopTable.offsetWidth + 1 : leftTopDiv.offsetWidth + 1;
	var RIGHT_WIDTH = (rightTopDiv.offsetWidth > rightTopTable.offsetWidth) ? rightTopTable.offsetWidth: rightTopDiv.offsetWidth;
	var TOP_HEIGHT = (rightTopDiv.offsetHeight > rightTopTable.offsetHeight) ? rightTopTable.offsetHeight : rightTopDiv.offsetHeight;
	var BOTTOM_HEIGHT = rightBottomDiv.offsetHeight > rightBottomTable.offsetHeight ? rightBottomTable.offsetHeight + 1: rightBottomDiv.offsetHeight + 1;

	// 2013/05/20 v5.2.2.23 start
	if (headerHidden)
	{
		TOP_HEIGHT = 0;
	}
	// 2013/05/20 v5.2.2.23 end
	
	var isLeftColumnHidden = false;

	if (leftColTags != null)
	{
		LEFT_WIDTH = 0;
		for (var i = 0; i < leftColTags.length; i++)
		{
			var width = (leftColTags[i].width == null || leftColTags[i].width == "") ?0 :parseInt(leftColTags[i].width);
			LEFT_WIDTH = LEFT_WIDTH + width;
		}
	}

//	if (headerStatus && detailStatus && leftTopTable.cells.length > 0 && leftBottomTable.cells.length > 0)
//	{
//		var leftCellList = leftTopTable.cells;
//		for (var i = 0; i < leftCellList.length; i++)
//		{
//			var cell = leftCellList[i];
//			if (cell.style.display != null && cell.style.display != "none")
//			{
//				isLeftColumnHidden = false;
//			}
//		}
//	}

//	if(!isLeftColumnHidden)
//		TOP_HEIGHT = (leftTopDiv.offsetHeight > leftTopTable.offsetHeight) ? leftTopTable.offsetHeight : leftTopDiv.offsetHeight;
	if(detailStatus && !isLeftColumnHidden)
		BOTTOM_HEIGHT = leftBottomDiv.offsetHeight > leftBottomTable.offsetHeight ? leftBottomTable.offsetHeight : leftBottomDiv.offsetHeight;

	//	<jp> 補正値を計算し、再度スクロールバーの表示位置を修正します。</jp>
	var scrollyFlg = false;
	var scrollxFlg = false;

	var scrollBarWidth = 0;
	var scrollBarHeight = 0;

//	scrollBarWidth = 17;
//	scrollBarHeight = 17;
	scrollBarWidth = document.all( divDetail ).offsetWidth - document.all( divDetail ).clientWidth;
	scrollBarHeight = document.all( divDetail ).offsetHeight - document.all( divDetail ).clientHeight;

	if( scrollDetailStatus )
	{
		leftTopDiv.style.width 		= LEFT_WIDTH;
		rightTopDiv.style.width 	= RIGHT_WIDTH;
		leftBottomDiv.style.width 	= LEFT_WIDTH;
		topDiv.style.width 			= LEFT_WIDTH + RIGHT_WIDTH;
		topDiv.style.height 		= TOP_HEIGHT;
		rightTopDiv.style.height 	= TOP_HEIGHT;
		leftTopDiv.style.height 	= TOP_HEIGHT;

		// 横スクロールが表示されない場合
		if(rightBottomDiv.offsetWidth > rightBottomTable.offsetWidth )
		{
			rightBottomDiv.style.overflowX = "hidden";
			bottomDiv.style.height 		= BOTTOM_HEIGHT + 1;
			// 2013/05/20 v5.2.2.23 start
			// mainDiv.style.height 		= topDiv.offsetHeight + bottomDiv.offsetHeight + 1;
			if (headerHidden)
			{
				mainDiv.style.height 		= bottomDiv.offsetHeight + 1;
			}
			else
			{
				mainDiv.style.height 		= topDiv.offsetHeight + bottomDiv.offsetHeight + 1;
			}
			// 2013/05/20 v5.2.2.23 end
			rightBottomDiv.style.height = BOTTOM_HEIGHT;
			leftBottomDiv.style.height = BOTTOM_HEIGHT;
			// 縦スクロールが表示されない場合
			if (rightBottomDiv.offsetHeight >= rightBottomTable.offsetHeight)
			{
				rightBottomDiv.style.overflowY = "hidden";
				rightBottomDiv.style.width 	= RIGHT_WIDTH + 1;
				leftBottomDiv.style.width 	= LEFT_WIDTH ;
				mainDiv.style.width 		= LEFT_WIDTH + RIGHT_WIDTH + 1;
				bottomDiv.style.width 		= LEFT_WIDTH + RIGHT_WIDTH + 1;
			}
			// 縦スクロールが表示される場合
			else
			{
				rightBottomDiv.style.overflowY = "scroll";
				mainDiv.style.width 		= LEFT_WIDTH + RIGHT_WIDTH + scrollBarWidth;
				bottomDiv.style.width 		= LEFT_WIDTH + RIGHT_WIDTH + scrollBarWidth;
				rightBottomDiv.style.width 	= RIGHT_WIDTH + scrollBarWidth;
			}
		}
		// 横スクロールが表示される場合
		else
		{
			rightBottomDiv.style.overflowX = "scroll";
			rightBottomDiv.style.height = BOTTOM_HEIGHT + scrollBarHeight;
			// +1は横スクロール表示時の対策
			// 2012/10/29 v5.2.2.15 不要な縦領域が確保される不具合の修正
			//mainDiv.style.height 		= topDiv.offsetHeight + bottomDiv.offsetHeight + 1;
			//bottomDiv.style.height 		= BOTTOM_HEIGHT + scrollBarHeight + 1;
			bottomDiv.style.height 		= BOTTOM_HEIGHT + scrollBarHeight + 1;
			// 2013/05/20 v5.2.2.23 start
			// mainDiv.style.height 		= topDiv.offsetHeight + bottomDiv.offsetHeight + 1;
			if (headerHidden)
			{
				mainDiv.style.height 		= bottomDiv.offsetHeight + 1;
			}
			else
			{
				mainDiv.style.height 		= topDiv.offsetHeight + bottomDiv.offsetHeight + 1;
			}
			// 2013/05/20 v5.2.2.23 end
			// 縦スクロールが表示されない場合
			if (rightBottomDiv.offsetHeight >= rightBottomTable.offsetHeight)
			{
				rightBottomDiv.style.overflowY = "hidden";
				mainDiv.style.width 		= LEFT_WIDTH + RIGHT_WIDTH + 1;
				bottomDiv.style.width 		= LEFT_WIDTH + RIGHT_WIDTH + 1;
				rightBottomDiv.style.width 	= RIGHT_WIDTH + 1;
			}
			// 縦スクロールが表示される場合
			else
			{
				rightBottomDiv.style.overflowY = "scroll";
				rightBottomDiv.style.width 	= RIGHT_WIDTH + scrollBarWidth;
				leftBottomDiv.style.width 	= LEFT_WIDTH;
				bottomDiv.style.width 		= LEFT_WIDTH + RIGHT_WIDTH + scrollBarWidth;
				mainDiv.style.width 		= LEFT_WIDTH + RIGHT_WIDTH +  scrollBarWidth;
			}
		}
		var nextDivHeight = ( rightBottomTable.offsetHeight > document.all( detail ).offsetHeight ) ? rightBottomTable.offsetHeight : document.all( detail ).offsetHeight;
	}
	// 右下が無い場合
	else
	{
		// 左下もない場合
		if (!detailStatus)
		{
			bottomDiv.style.display			= "none";
			leftBottomDiv.style.display		= "none";
			rightBottomDiv.style.display	= "none";
			mainDiv.style.height			= TOP_HEIGHT;
			topDiv.style.height				= TOP_HEIGHT;
			leftTopDiv.style.height			= TOP_HEIGHT;
			rightTopDiv.style.height		= TOP_HEIGHT;

			rightBottomDiv.style.height	= 0;
			
			if(fixedBank != colSize)
			{
				mainDiv.style.width			= LEFT_WIDTH + RIGHT_WIDTH;
				topDiv.style.width			= LEFT_WIDTH + RIGHT_WIDTH;
				leftTopDiv.style.width		= LEFT_WIDTH;
				leftBottomDiv.style.width	= LEFT_WIDTH;
				rightTopDiv.style.width		= RIGHT_WIDTH;

				if (rightTopDiv.offsetWidth < rightTopTable.offsetWidth)
				{
					rightTopDiv.style.overflowX = "scroll";
					rightTopDiv.style.height 	= TOP_HEIGHT + scrollBarHeight;
					rightTopDiv.style.height 	= TOP_HEIGHT + scrollBarHeight;
					mainDiv.style.height		= TOP_HEIGHT + scrollBarHeight;
					topDiv.style.height			= TOP_HEIGHT + scrollBarHeight;
				}
			}
			else
			{
				mainDiv.style.overflow			= "visible";
				topDiv.style.overflow			= "visible";
				rightTopDiv.style.overflow		= "visible";
				leftTopDiv.style.overflow		= "visible";
			}

		}
		// 左下だけある場合(通常のリストセル)
		else
		{
			mainDiv.style.overflow			= "visible";
			topDiv.style.overflow			= "visible";
			bottomDiv.style.overflow		= "visible";
			leftTopDiv.style.overflow		= "visible";
			leftBottomDiv.style.overflow	= "visible";
			rightTopDiv.style.overflow		= "visible";
			rightBottomDiv.style.overflow	= "visible";
		}
	}
	// 2012/08/31 v5.2.2.13 ヘッダを非表示にしてもスペースがあいて表示される不具合を修正
	if (headerHidden) {
		topDiv.style.display = "none";
	}
}

//Add Start 2010/02/24 T.Sumiyama for v5.2
function calendar(calnderId, sourceButton, displyFormat)
{
	// ボタンからフォーカスをはずします
	var activeobj = document.activeElement;
	activeobj.blur();
	var obj = document.getElementById(calnderId);
	if (!obj)
	{
		alert('No Id exists');
	}
	var languageCode = document.getElementById('calendarLan').value;
	calendarPrepare(languageCode);
	displayCalendar(obj, displyFormat, sourceButton, false);
	event.cancelBubble = true;
    event.returnValue = false;
}
// Add End 2010/02/24 T.Sumiyama for v5.2


// Add Start 2010/11/01 T.Sumiyama for v5.2
function setListCellHeaderHeight(id)
{
	// 各行の変更後の高さを入れておくための配列 [キー名は(id + 固定列orスクロール列 + 行）]
	var theadTr = new Array();

	// ListCell要素取得
	var ele = document.getElementById(id);
	
	// v5.2.2.25 2014/08/26
	if (!ele) {
		return;
	}
	
	// ListCellのThead要素取得(固定とスクロールのTableの場合lengthは2)
	var headEle = ele.getElementsByTagName("thead");

	for (var i = 0; i < headEle.length; i++)
	{
		// ListCellのTh要素配列取得
		var trEle = headEle[i].getElementsByTagName("tr");
		for (var j = 0; j < trEle.length;j++)
		{
			// ListCellのTh要素取得
			var thisTr = trEle[j];
			var thisHeight = 0;
			// ListCellのHeaderのテキストが入ったspan要素配列取得
			var spans = thisTr.getElementsByTagName("span");
			for (var k = 0; k < spans.length; k++)
			{
				var headerSpan = spans[k];
				if (headerSpan.className == "headerSpan")
				{
					// height と lineHeightの初期化
					headerSpan.style.lineHeight = "";
					headerSpan.style.height = "";

					// 2012/09/12 v5.2.2.13 Headerの高さを計算する時にspanのoffsetheightだけではなくtdのoffsetHeightも考慮するように修正(固定列が結合だけの列の場合、レイアウトが崩れる不具合があった為)
					
					
					// rowSpanがある場合
					if (headerSpan.parentNode.rowSpan > 1)
					{
						// rowSpanでspanの高さを割り、１行分に換算
						//var perRow = headerSpan.offsetHeight / headerSpan.parentNode.rowSpan;
						var perRow = Math.max(headerSpan.offsetHeight + 4, headerSpan.parentNode.offsetHeight) / headerSpan.parentNode.rowSpan;
						//thisHeight = thisHeight < perRow ?perRow :thisHeight;
						thisHeight = Math.max(thisHeight, perRow);
						for (var l = 0; l < headerSpan.parentNode.rowSpan; l++)
						{
							// 結合行の高さを均等に割り当て
							theadTr[id + i + ":" + (j + l)] = thisHeight;
						}
					}
					else
					{
						//thisHeight = thisHeight < headerSpan.offsetHeight ?headerSpan.offsetHeight :thisHeight;
						thisHeight = Math.max(thisHeight, Math.max(headerSpan.offsetHeight + 4, headerSpan.parentNode.offsetHeight));
					}
					theadTr[id + i + ":" + j] = thisHeight;
				}
			}
			thisTr.style.height = thisHeight + 4;
		}
	}

	/* Theadが複数ある場合、同期させるための処理  */
	if (headEle.length > 1)
	{
		// 固定列・スクロール列の大きいほういずれかに同期
		var headerHeight = new Array();
		for (var i = 0; i < headEle.length; i++)
		{
			var trEle = headEle[i].getElementsByTagName("tr");
			for (var j = 0; j < trEle.length;j++)
			{
				var height = 0;
				for (var k = 0; k < headEle.length; k++)
				{
					if (theadTr[id + k + ":" + j] != null
							&& theadTr[id + k + ":" + j] != "undefined" )
					{
						height = theadTr[id + k + ":" + j] > height ?theadTr[id + k + ":" + j] :height;
					}
					headerHeight[j] = height;
				}
			}
		}
		
		/* 行の高さを再設定します */
		for (var i = 0; i < headEle.length; i++)
		{
			var trEle = headEle[i].getElementsByTagName("tr");
			for (var j = 0; j < trEle.length;j++)
			{
				var thisTr = trEle[j];
				if(thisTr.getElementsByTagName("TD") !=null
						&& thisTr.getElementsByTagName("TD").length > 0)
				{
					for(var k = 0; k < thisTr.getElementsByTagName("TD").length; k++)
					{
						var thisTd = thisTr.getElementsByTagName("TD")[k];
						if (thisTd.rowSpan != null && thisTd.rowSpan > 1)
						{
							var height = 0
							for (var l = 0; l < thisTd.rowSpan; l++)
							{
								height += headerHeight[j + l];
							}
							thisTd.style.height = height;
						}

					}
				}
				thisTr.style.height = headerHeight[j];
			}
		}
	}
}
// Add End 2010/11/01 T.Sumiyama for v5.2