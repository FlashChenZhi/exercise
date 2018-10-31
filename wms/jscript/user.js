// $Id: user.js 6786 2010-01-22 06:47:48Z okayama $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * ユーザが使用するJavaScriptです。
 * 関数や変数を追加するときは"_"を関数名の先頭に付加して下さい。
 * @version $Revision: 6786 $, $Date: 2010-01-22 15:47:48 +0900 (金, 22 1 2010) $
 * @author  $Author: okayama $
 */
/*--------------------------------------------------------------------------*/
/* グローバル変数															*/
/*--------------------------------------------------------------------------*/

	/*リストセル　ハイライト表示の背景色*/
	var _rowBgColor    = "#A4A4FF";
	/*リストセル　ハイライト表示の文字色*/
	var _rowStyleColor = "black";

	/*メイン画面サイズ　高さ*/
	var _mainWindowWidth = 1280;
	/*メイン画面サイズ　横幅*/
	var _mainWindowHeight = 1024;


/*--------------------------------------------------------------------------*/
/* 関数																		*/
/*--------------------------------------------------------------------------*/

/** <jp>
 * [関数の説明]
 * 引数で指定された日付がyyyy/MM/ddに一致するか判定します。
 * [引数の説明]
 * date 入力された値
 * [戻り]
 * 入力値がフォーマットに一致している場合true、一致しない場合false
 </jp> */

function _DateCheck(date,locale)
{

    if(date == "") return true;
    hiduke = date

    //日本語の場合、yyyy/MM/ddの形式になっているかチェックする
	if(locale == "ja")
	{
	   	if (! hiduke.match(/^\d{1,4}\/\d{1,2}\/\d{1,2}$/) &&  ! hiduke.match(/^\d\d\d\d\d\d\d\d$/))
		{
        	return false
    	}
	}
    //中国語の場合
    if(locale == "zh")
    {
    	//yyyy-MM-ddの形式になっているかのチェック
        if(! hiduke.match(/^\d{1,4}\-\d{1,2}\-\d{1,2}$/) &&  ! hiduke.match(/^\d\d\d\d\d\d\d\d$/))
        {
        	return false
        }
        //"－"で区切られているので、"/"に置換する
        hiduke = date.replace("-","/")
        hiduke = hiduke.replace("-","/")
    }
	//英語の場合、MM/dd/yyyyの形式になっているかチェックする.
	if(locale == "en")
	{
	    if (! hiduke.match(/^\d{1,2}\/\d{1,2}\/\d{1,4}$/) &&  ! hiduke.match(/^\d\d\d\d\d\d\d\d$/))
		{
    	    return false
	    }
	}


	a = new Array();

	if(locale == "ja" || locale == "zh")
	{
		if(hiduke.match(/^\d{1,4}\/\d{1,2}\/\d{1,2}$/))
		{
			a = hiduke.split("/")
		}
	}
    else
    {
		if(hiduke.match(/^\d{1,2}\/\d{1,2}\/\d{1,4}$/))
    	{
    		a = hiduke.split("/")
    		i = a[0]
    		j = a[1]
    		k = a[2]
    		a[0] = k
    		a[1] = i
    		a[2] = j
    	}
    }

    //入力桁数によって年数を補完する
   	if(a[0].length == 1)
   	{
   		a[0] = 000 + a[0];
   	}
   	if(a[0].length == 2)
   	{
   		a[0] = 00 + a[0];
   	}
   	if(a[0].length == 3)
   	{
   		a[0] = 0 + a[0];
   	}

   	// 0年を指定していないかチェックする
   	if(a[0].match(/0000/)) return false;

    nd = new Date();
    yy = nd.getFullYear()
    mm = nd.getMonth()*1 + 1
    dd = nd.getDate()

	//月のチェック
    if (a[1]*1 > 12 || a[1]*1 < 1)
    {
       	return false
    }
	//日のチェック
	//31日までのとき
    if (a[1]*1 == 1 || a[1]*1 == 3 || a[1]*1 == 5 || a[1]*1 == 7 || a[1]*1 == 8 || a[1]*1 == 10 || a[1]*1 == 12)
    {
       	if (a[2]*1 > 31 || a[2]*1 < 1)
       	{
        	return false
        }
    }
    else
    {
	//2月のチェック
        if (a[1]*1 == 2)
        {
            if (a[0] % 4 == 0)
            {
                if (a[2]*1 > 29 || a[2]*1 < 1)
                {
                    return false
                }
            }
            else
            {
                if (a[2]*1 > 28 || a[2]*1 < 1)
                {
                    return false
                }
            }
        }
        else
        {
		//30日が末日のチェック
            if (a[2]*1 > 30 || a[2]*1 < 1)
            {
                return false
            }
        }
    }
    return true
}

/** <jp>
 * [関数の説明]
 * 時刻のフォーマットが正しいかチェックします。
 *
 * [引数の説明]
 * str　入力された値　例9-9-9
 * format　エリアに対応する棚フォーマット　例99-999-999
 * [戻り]
 * 入力値がフォーマットに一致している場合true、一致しない場合false
 </jp> */
function _isTimeFormat(time)
{
	if(time == "") return true;
	if(!time.match(/^([01]?[0-9]|2[0-3]):([0-5]?[0-9]):([0-5]?[0-9])$/)) return false;
	return true;
}

/** <jp>
 * [関数の説明]
 * 引数formatで指定されたフォーマットに引数strが一致するかどうかを判定します。棚フォーマットチェックで
 * 使用します。
 * [引数の説明]
 * str　入力された値　例9-9-9
 * format　エリアに対応する棚フォーマット　例99-999-999
 * [戻り]
 * 入力値がフォーマットに一致している場合true、一致しない場合false
 </jp> */

function _isFormatCheck(str, format)
{
    //入力文字がない場合trueで返す
	if(str == "") return true;

    // ハイフン(-) が無い場合のチェックを最初に行います。
    // 数字のみの場合のチェック
	if(format.match(/[0-9]+/))
	{
	    if(!str.match(/[0-9]+/)) return false;
		if(str.match(/[0-9]+[a-zA-Z]+/)) return false;
	    if(str.match(/[a-zA-Z]+[0-9]+/)) return false;
	    if(str.length > format.length) return false;
	}
    // 文字列のみの場合のチェック
    else if(format.match(/[a-zA-Z]+/))
    {
    	informat = format;
    	instr = str;
    	var informat = informat.slice(0, informat.indexOf('-'));
    	var instr = instr.slice(0, instr.indexOf('-'));
    	if(informat.length < instr.length) return false;
    	if(instr.match(/[\W]+/)) return false;
    	if(instr.match(/[^a-zA-Z0-9]+/)) return false;
    }

    // フォーマットにハイフン(-)が含まれている場合のチェックを行います。
    while(format.indexOf('-')!=-1)
    {
    　　// ハイフンが含まれていたら場合 falseを返します。
      　if( str.indexOf('-')==-1 ) return false;

 	    var strChar = str.slice( 0, str.indexOf('-') );
		var formatChar = format.slice( 0, format.indexOf('-') );

        // formatCharが数字時のチェック
	    if(formatChar.match(/[0-9]+/))
     	{
     		if(!strChar.match(/[0-9]+/)) return false;
	        if(strChar.match(/[0-9]*[\D]+[0-9]*/)) return false;
	        if(strChar.match(/[0-9]+[a-zA-Z]+/)) return false;
	        if(strChar.match(/[a-zA-Z]+[0-9]+/)) return false;
	        if(strChar.length > formatChar.length) return false;
	    }
        // formatCharが文字列時チェック
        else if(formatChar.match(/[a-zA-Z]+/))
        {
			if(formatChar.length != strChar.length) return false;
			if(strChar.match(/[\W]+/)) return false;
	    	if(strChar.match(/[^a-zA-Z0-9]+/)) return false;
        }
        str = str.slice( str.indexOf('-')+1 );
	　　format = format.slice(format.indexOf('-')+1);

		if(format.indexOf('-')==-1)
		{
			var strChar = str.slice( 0, str.length );
			var formatChar = format.slice( 0, format.length );

			// formatCharが数字時のチェック
			if(formatChar.match(/[0-9]+/))
			{
				if(!strChar.match(/[0-9]+/)) return false;
				if(strChar.match(/[0-9]*[\D]+[0-9]*/)) return false;
				if(strChar.match(/[0-9]+[a-zA-Z]+/)) return false;
				if(strChar.match(/[a-zA-Z]+[0-9]+/)) return false;
				if(strChar.length > formatChar.length) return false;
			}
			// formatCharが文字列時チェック
			else if(formatChar.match(/[a-zA-Z]+/))
			{
				if(formatChar.length != strChar.length) return false;
				if(strChar.match(/[\W]+/)) return false;
				if(strChar.match(/[^a-zA-Z0-9]+/)) return false;
			}
		}
	}

    str = str.slice( str.indexOf('') );
    format = format.slice(format.indexOf(''));
    // formatCharが数字時のチェック
	if(format.match(/[0-9]+/))
    {
    	if(str.match(/-/)) return false;
    	if(!str.match(/[0-9]+/)) return false;
    	if(str.match(/[0-9]*[\D]+[0-9]*/)) return false;
	    if(str.match(/[0-9]+[a-zA-Z]+/)) return false;
	    if(str.match(/[a-zA-Z]+[0-9]+/)) return false;
		if(str.length > format.length) return false;
	}
    // formatCharが文字列時チェック(禁止文字チェックは入っていません。
    else if(format.match(/[a-zA-Z]+/))
    {
		if(format.length < str.length) return false;
		if(str.match(/[\W]+/)) return false;
      	if(str.match(/[^a-zA-Z0-9]+/)) return false;
    }
    return true;
}

/** <jp>
 * [関数の説明]
 *  objで指定したオブジェクトのvalueが引数minより小さいかどうかを確認します。小さい場合はmsgnoで指定したメッセージ
 * をアラートで表示します。
 * [引数の説明]
 * min   最小値
 * msgno Message No.(例：MSG-0001)
 * obj thisを指定
 * [戻り]
 * minより値が小さい場合にアラートを表示します。
 </jp> */
/** <en>
 * [Explanation of a function]
 * It confirms whether value of the object specified with obj is smaller than an argument min. When it is small, the message specified with msgno is indicated by the alert.
 * [The custom-made tag name to be used]
 * Common
 * [Explanation of an argument]
 * min   minimum value
 * msgno Message No.(Ex:MSG-0001)
 * obj Set "this" pointer.
 * [Return]
 * An alert is indicated when value is smaller than min.
 </en> */
function _checkMinValue(min, msgno, obj)
{
 if(obj.value == "") return true;
 /* Remove comma. */
 if(getString(obj.value, ',') < min)
 {
  alertOutput (msgno);
  obj.focus();
  return false;
 }
 return true;
}

/**
 * [関数の説明]
 *  objで指定したオブジェクトのvalueが引数minより大きいかどうかを確認します。小さい場合はmsgnoで指定したメッセージ
 * をアラートで表示します。
 * [引数の説明]
 * min   最小値
 * msgno Message No.(例：MSG-0001)
 * obj thisを指定
 * [戻り]
 * 値がmin以下の場合にアラートを表示します。
 */
function _checkOverValue(min, msgno, obj)
{
 if(obj.value == "") return true;
 /* Remove comma. */
 if(getString(obj.value, ',') <= min)
 {
  alertOutput (msgno);
  obj.focus();
  return false;
 }
 return true;
}



/**
 * [関数の説明]
 *　ボタン押下時にポップアップウィンドウを閉じて
 *  親画面にフォーカスを戻します。
 */
function _closeWindow()
{
	window.close();
	opener.window.focus();
}

/**
 * [関数の説明]
 *　データ報告（予定作業報告）画面で使用する関数です。
 *  チェックボックスにチェックが入ったときに隣のチェックボックスのDisabledを切り替えます。
 */
function _changeDisabled(arg)
{
	var colArray = new Array("chk_ComUseEventSelect","chk_ComUseEventWorkProg","chk_ComUseRupwi");
	var rowArray = new Array("Instk","Strg","Rtrivl","Pick","Shp");
	var col = 0;
	var row = 0;

	for(var i=0;i < colArray.length; i++)
	{
		if(arg.name.indexOf(colArray[i]) >= 0)
		{
			col = i;
			break;
		}
	}
	for(var j=0; j < rowArray.length; j++)
	{
		if(arg.name.indexOf(rowArray[j]) >= 0)
		{
			row = j;
			break;
		}
	}

	if(col == 0)
	{
		var obj_1 = colArray[col+1] + rowArray[row];
		var obj_2 = colArray[col+2] + rowArray[row];
		if(arg.checked)
		{
			if(document.all(obj_1))
			{
				document.all(obj_1).disabled=false;
			}
			else
			{
				document.all(obj_2).disabled=false;
			}
		}
		else
		{
			if(document.all(obj_1))
			{
				document.all(obj_1).disabled=true;
				document.all(obj_1).checked = false;
			}
			if(document.all(obj_2))
			{
				document.all(obj_2).disabled=true;
				document.all(obj_2).checked = false;
			}
		}
	}
	else
	{
		var obj_1 = colArray[col+1] + rowArray[row];
		if(arg.checked)
		{
			document.all(obj_1).disabled=false;
		}
		else
		{
			document.all(obj_1).disabled=true;
			document.all(obj_1).checked = false;
		}
	}
}


/**
 * [関数の説明]
 * メッセージエリアへメッセージをセットします。
 * 引数のflagを変更することで、アイコンを使い分けることができます。
 * flag : 0 無し
 *        1 Information
 *        2 Attention
 *        3 Warning
 *        4 Error
 * [引数の説明]
 * msg : メッセージエリアで表示するメッセージ
 * flag : アイコンの種類
 */
function _setMessage(msg, flag)
{
	if(!document.all.message)return;
	var source = "";
	if(flag==1)
	{
		source = "<img src=\"" + contextPath + "/img/control/message/Information.gif\" ";
	}
	else if(flag==2)
	{
		source = "<img src=\"" + contextPath + "/img/control/message/Attention.gif\" ";
	}
	else if(flag==3)
	{
		source = "<img src=\"" + contextPath + "/img/control/message/Warning.gif\" ";
	}
	else if(flag==4)
	{
		source = "<img src=\"" + contextPath + "/img/control/message/Error.gif\" " ;
	}
	else
	{
		source = "<img src=\"" + contextPath + "/img/control/message/None.gif\" " ;
	}
	document.all.message.innerHTML = source +
			"width=\"15\" height=\"15\" style=\"vertical-align : text-top;\">"    +
			"<img src=\"" + contextPath + "/img/control/message/Spacer.gif\" "    +
			"width=\"4\"  height=\"19\">" + msg;
}

/**
 * [関数の説明]
 * メッセージエリアへメッセージをセットします。ハイライトつきメッセージを表示します。
 * プロダクト用にdynamicaction.jsから移動
 * 引数のflagを変更することで、アイコンを使い分けることができます。
 * flag : 0 無し
 *        1 Information
 *        2 Attention
 *        3 Warning
 *        4 Error
 * [引数の説明]
 * msg : メッセージエリアで表示するメッセージ
 * flag : アイコンの種類
 */
function _setdaMessage(msg, flag)
{
	if(!document.all.message)return;
	var source = "";

	if(!document.all.message)return;

	var balloon = document.all.message.balloon;
	var balloonLevel = document.all.message.balloonLevel;

	var source = "";
	var Css = "";
	var fontCss = "";
	var defineLevel = "";

	if(flag==1 )
	{
		source = "<img src=\"" + contextPath + "/img/control/message/Information.gif\" ";
		Css = "balloon-info";
		fontCss = "balloon-info-font";
		defineLevel = "Info";
	}
	else if(flag==2 )
	{
		source = "<img src=\"" + contextPath + "/img/control/message/Attention.gif\" ";
		Css = "balloon-attention";
		fontCss = "balloon-attention-font";
		defineLevel = "Attention";
	}
	else if(flag==3 )
	{
		source = "<img src=\"" + contextPath + "/img/control/message/Warning.gif\" ";
		Css = "balloon-warning";
		fontCss = "balloon-warning-font";
		defineLevel = "Warning";
	}
	else if(flag==4 )
	{
		source = "<img src=\"" + contextPath + "/img/control/message/Error.gif\" " ;
		Css = "balloon-error";
		fontCss = "balloon-error-font";
		defineLevel = "Error";
	}
	else
	{
		source = "<img src=\"" + contextPath + "/img/control/message/None.gif\" " ;
		Css = "balloon-outsiderange";
		fontCss = "balloon-outsiderange-font";
		defineLevel = "OutsideRange";
	}

	//Add 2009/06/10 v5.0.0.0 メッセージが所定サイズを超えていた場合、所定サイズで切り出し、"..."を付加します。
	var tempStr = msg;

	if(MESSAGE_MAX_SIZE <= getByte(msg))
	{
		var realLen = msg.length;
		var cutLen = MESSAGE_MAX_SIZE;
		for(var i = 0; i < MESSAGE_MAX_SIZE; i++)
		{
			cutLen = MESSAGE_MAX_SIZE -i;
			if(cutLen < realLen)
			{
				tempStr = msg.substring(0, cutLen);
				if(getByte(tempStr) <= MESSAGE_MAX_SIZE)
				{
					tempStr = tempStr + "...";
					break;
				}
			}
		}
	}
	//End 2009/06/10 v5.0.0.0

	document.all.message.innerHTML = source +
			"width=\"15\" height=\"15\" style=\"vertical-align : text-top;\">"    +
			"<img src=\"" + contextPath + "/img/control/message/Spacer.gif\" "    +
			"width=\"4\"  height=\"19\">" + tempStr;

	if ( balloon == "true" && (balloonLevel.toUpperCase().indexOf( defineLevel.toUpperCase() ) != -1) )
	{
		clearExistingBdMsg();
		bdMsg(tempStr, Css, fontCss);
	}

}


/**
 * [関数の説明]
 * リストセルにデータがあるかを判断します。
 * リストセルにデータが無い場合、trueを返します。
 * [引数の説明]
 * lstID : リストセルのID
 */
function _isListcellEmpty(lstID)
{
	if (document.all(lstID + "$1$1") == null)
	{
		return true;
	}
	return false;
}

/**
 * [関数の説明]
 * 引数で与えられたコントロールに値が入力されているかを確認し、値が無い場合
 * falseを返します。
 * 半角空白を取り除き、文字列のサイズが0の場合はfalseを返します。
 * 全角空白は1文字と数えるためその場合、trueを返します。
 * [使用するカスタムタグ名]
 * SubmitButtonTag
 * [引数の説明]
 * txtID チェックを行うコントロールのID
 */
function _isValueInput(txtID)
{
	if(!validate(document.all(txtID).value))
	{
		return false;
	}
	else
	{
		return true;
	}
}


/**
 * [関数の説明]
 * リストセルに配置されたチェックボックスのチェック状態を調べます。
 * チェックボックスは１列目に配置されている必要があります。
 * チェックが１つでも入っている場合はtrueを返します。
 * 全てのチェックがはずれているときfalseを返します。
 * [引数の説明]
 * lstID : リストセルのID
 */
function _isListcellChecked(lstID)
{
	var i = 1;
	while(document.all(lstID + '$' + i + '$1' ) != null)
	{
		if(document.all(lstID + '$' + i + '$1' ).checked) return true;
		i++;
	}
	return false;
}

/**
 * [関数の説明]
 * 範囲指定の大小が正しいかどうかチェックします。
 * 範囲指定の開始と終了を比較し、開始の方が大きい場合はfalseを返します。
 * 開始の方が小さい場合はtrueを返します。
 * [引数の説明]
 * from : 比較対象の開始
 * end  : 比較対象の終了
 */
function _isCorrectRange(from, end)
{
	if (from <= end)
	{
		return true;
	}
	return false;

}

/**
 * [関数の説明]
 * 引数で与えられたコントロールの値が禁止文字ではないか確認し、
 * 禁止文字であった場合は、falseを返します。
 *
 * [引数の説明]
 * txtID チェックを行うコントロールのID
 */
function _isNgCode(txtID)
{
	if (document.all(txtID).value.indexOf("*") == -1)
	{
		return false;
	}
	else
	{
		return true;
	}

}

/**
 * WindowsXP_SP2環境下でファイルブラウズエラーが発生した場合
 * そのエラー通知がサーバにサブミットされません。
 * その為、エラー発生時なにか処理を行いたい場合は、
 * その処理をこちらに記述してください。
 */
function _doFileBrowsOnError()
{
}

/**
 * リストセルのセル内に対してフォーカスをセットします。
 * @param id リストセルのID
 * @param row 行
 * @param col 列
 * @return なし
 */
function _setListCellFocus(id, row, col)
{
	id = id + "$" + row + "$" + col;
	if(document.getElementById(id) != null)
	{
		if(xmlhttpRequest)
		{
			asyncFocusIs = id;
		}
		else
		{
			document.getElementById(id).focus();
		}
	}
}
