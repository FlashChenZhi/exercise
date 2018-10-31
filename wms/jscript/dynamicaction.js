// $Id: dynamicaction.js 7996 2011-07-06 00:52:24Z kitamaki $

/**
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
 
/*--------------------------------------------------------------------------*/
/*<jp> グローバル変数</jp>*/
/*--------------------------------------------------------------------------*/

/**<jp>日付区切り文字</jp>*/
var DATE_SEPARATOR = "/";

/**<jp>時間区切り文字</jp>*/
var TIME_SEPARATOR = ":";

/**<jp>メッセージの最大表示サイズ</jp>*/
var MESSAGE_MAX_SIZE = 110;

/*--------------------------------------------------------------------------*/
/*<jp>関数</jp>*/
/*--------------------------------------------------------------------------*/

/** <jp>
 * [関数の説明]
 * JSPに定義されたDynamicAction関数を呼び出します。JSPに定義されていない場合はエラーとせずに、そのまま抜けます。
 </jp> */
function __callDynamicAction()
{
	if( typeof __dynamicAction != 'undefined' )
	{
		if( typeof targetElement != 'undefined' )
		{
			if (event.type == 'click')
			{
				targetElement = event.srcElement.id;
			}
		}
		if( __dynamicAction() ) event.returnValue = false;

		// mod 2009/03/25 v4.4.0.2  T.Ogawa
		// JSP手書きのdynamicAction()がある場合、下記コードにてtargetElementがクリアされてしまうと、
		// FuncionKey押下時の手書きのdynamicAction()にtargetElementがブランクで渡されてしまい
		// 正常に動作しない不具合があった為コメントアウトする。
		// (targetElementはこの関数が呼ばれる前に必ず設定される為、ブランクに戻さなくても動作に影響はない)
		//if( typeof targetElement != 'undefined' )
		//{
			//targetElement = "";
		//}
	}
	return true;
}

/** <jp>
 * [関数の説明]
 * ダイナミックアクションで実行する必須入力チェック。
 * [引数の説明]
 * arguments[0] メッセージのファシリティ
 * arguments[1] メッセージ
 * arguments[2] 対象コントロールの配列
 * [戻り]
 * true - OK false - NG
 </jp> */
function __da_requrired()
{
	var facility = __da_requrired.arguments[0];
	var msg      = __da_requrired.arguments[1];
	var controls = __da_requrired.arguments[2];

	for ( var i = 0, length = controls.length; i < length; i++ )
	{
		var control = controls[i];

		if ( control == null )
		{
			// No Check
			continue;
		}

		if ( ( control.type == "text" && !__da_isInputValue( control.value ) ) ||
		     ( control.type == "checkbox" && !control.checked ) )
		{
			__da_setMessage(msg, facility);
			control.focus();
			return false;
		}
	}

	return true;
}

/** <jp>
 * [関数の説明]
 * ダイナミックアクションで実行する禁止文字チェック。
 * [引数の説明]
 * arguments[0] メッセージのファシリティ
 * arguments[1] メッセージ
 * arguments[2] 対象コントロールの配列
 * [戻り]
 * true - OK false - NG
 </jp> */
function __da_prohibition()
{
	if (!__prohibition)
	{
		return true;
	}

	var facility = __da_prohibition.arguments[0];
	var msg      = __da_prohibition.arguments[1];
	var controls = __da_prohibition.arguments[2];

	for ( var i = 0, length = controls.length; i < length; i++ )
	{
		var control = controls[i];

		if ( control == null )
		{
			// No Check
			continue;
		}

		if ( isProhibitedCharacter(control.value, __prohibition) )
		{
			__da_setMessage(msg, facility);
			control.focus();
			return false;
		}
	}

	return true;
}

/** <jp>
 * [関数の説明]
 * ダイナミックアクションで実行する日付チェックのMM/dd/yyyy版。
 * [引数の説明]
 * arguments[0] メッセージのファシリティ
 * arguments[1] メッセージ
 * arguments[2] 対象コントロールの配列
 * [戻り]
 * true - OK false - NG
 </jp> */
function __da_rangeDate_MMddyyyy()
{
	var facility = __da_rangeDate_MMddyyyy.arguments[0];
	var msg      = __da_rangeDate_MMddyyyy.arguments[1];
	var controls = __da_rangeDate_MMddyyyy.arguments[2];

	if ( controls.length < 2 )
	{
		// No Check
		return true;
	}

	var from = controls[0];
	var to = controls[1];

	if ( from == null )
	{
		// No Check
		return true;
	}

	if ( to == null )
	{
		// No Check
		return true;
	}

	if ( !__da_dateTimeRangeCheck( from.value, "00:00:00", to.value, "00:00:00", 2, 0, 1 ) )
	{
		__da_setMessage(msg, facility);
		to.focus();
		return false;
	}

	return true;
}

/** <jp>
 * [関数の説明]
 * ダイナミックアクションで実行する日付チェックのyyyy/MM/dd版。
 * [引数の説明]
 * arguments[0] メッセージのファシリティ
 * arguments[1] メッセージ
 * arguments[2] 対象コントロールの配列
 * [戻り]
 * true - OK false - NG
 </jp> */
function __da_rangeDate_yyyyMMdd()
{
	var facility = __da_rangeDate_yyyyMMdd.arguments[0];
	var msg      = __da_rangeDate_yyyyMMdd.arguments[1];
	var controls = __da_rangeDate_yyyyMMdd.arguments[2];

	if ( controls.length < 2 )
	{
		// No Check
		return true;
	}

	var from = controls[0];
	var to = controls[1];

	if ( from == null )
	{
		// No Check
		return true;
	}

	if ( to == null )
	{
		// No Check
		return true;
	}

	if ( !__da_dateTimeRangeCheck( from.value, "00:00:00", to.value, "00:00:00", 0, 1, 2 ) )
	{
		__da_setMessage(msg, facility);
		to.focus();
		return false;
	}

	return true;
}

/** <jp>
 * [関数の説明]
 * ダイナミックアクションで実行する日付時間チェックのMM/dd/yyyy HH:mm:ss版。
 * [引数の説明]
 * arguments[0] メッセージのファシリティ
 * arguments[1] メッセージ
 * arguments[2] 対象コントロールの配列
 * [戻り]
 * true - OK false - NG
 </jp> */
function __da_rangeDateTime_MMddyyyy()
{
	var facility = __da_rangeDateTime_MMddyyyy.arguments[0];
	var msg      = __da_rangeDateTime_MMddyyyy.arguments[1];
	var controls = __da_rangeDateTime_MMddyyyy.arguments[2];

	if ( controls.length < 4 )
	{
		// No Check
		return true;
	}

	var fromDate = controls[0];
	var fromTime = controls[1];
	var toDate = controls[2];
	var toTime = controls[3];

	if ( fromDate == null )
	{
		// No Check
		return true;
	}

	if ( fromTime == null )
	{
		// No Check
		return true;
	}

	if ( toDate == null )
	{
		// No Check
		return true;
	}

	if ( toTime == null )
	{
		// No Check
		return true;
	}

	if ( !__da_dateTimeRangeCheck( fromDate.value, fromTime.value, toDate.value, toTime.value, 2, 0, 1 ) )
	{
		__da_setMessage(msg, facility);
		toDate.focus();
		return false;
	}

	return true;
}

/** <jp>
 * [関数の説明]
 * ダイナミックアクションで実行する日付時間チェックのyyyy/MM/dd HH:mm:ss版。
 * [引数の説明]
 * arguments[0] メッセージのファシリティ
 * arguments[1] メッセージ
 * arguments[2] 対象コントロールの配列
 * [戻り]
 * true - OK false - NG
 </jp> */
function __da_rangeDateTime_yyyyMMdd()
{
	var facility = __da_rangeDateTime_yyyyMMdd.arguments[0];
	var msg      = __da_rangeDateTime_yyyyMMdd.arguments[1];
	var controls = __da_rangeDateTime_yyyyMMdd.arguments[2];

	if ( controls.length < 4 )
	{
		// No Check
		return true;
	}

	var fromDate = controls[0];
	var fromTime = controls[1];
	var toDate = controls[2];
	var toTime = controls[3];

	if ( fromDate == null )
	{
		// No Check
		return true;
	}

	if ( fromTime == null )
	{
		// No Check
		return true;
	}

	if ( toDate == null )
	{
		// No Check
		return true;
	}

	if ( toTime == null )
	{
		// No Check
		return true;
	}

	if ( !__da_dateTimeRangeCheck( fromDate.value, fromTime.value, toDate.value, toTime.value, 0, 1, 2 ) )
	{
		__da_setMessage(msg, facility);
		toDate.focus();
		return false;
	}

	return true;
}

/** <jp>
 * [関数の説明]
 * ダイナミックアクションで実行する数値範囲チェックの999,999,999.999版。
 * [引数の説明]
 * arguments[0] メッセージのファシリティ
 * arguments[1] メッセージ
 * arguments[2] 対象コントロールの配列
 * [戻り]
 * true - OK false - NG
 </jp> */
function __da_rangeNumber1()
{
	var facility = __da_rangeNumber1.arguments[0];
	var msg      = __da_rangeNumber1.arguments[1];
	var controls = __da_rangeNumber1.arguments[2];

	if ( controls.length < 2 )
	{
		// No Check
		return true;
	}

	var from = controls[0];
	var to = controls[1];

	if ( from == null )
	{
		// No Check
		return true;
	}

	if ( to == null )
	{
		// No Check
		return true;
	}

	var fromVal = replaceAll(from.value, ",", "");
	var toVal = replaceAll(to.value, ",", "");

	if ( __da_isInputValue( fromVal ) && __da_isInputValue( toVal ) )
	{
		if ( isNaN( fromVal ) || isNaN( toVal ) )
		{
			// No Check
			return true;
		}

		fromVal = new Number(fromVal);
		toVal = new Number(toVal);

		if ( fromVal <= toVal ) 
		{
			return true;
		}
		else
		{
			__da_setMessage(msg, facility);
			to.focus();
			return false;
		}
	}

	return true;
}

/** <jp>
 * [関数の説明]
 * ダイナミックアクションで実行する数値範囲チェックの999.999.999,999版。
 * [引数の説明]
 * arguments[0] メッセージのファシリティ
 * arguments[1] メッセージ
 * arguments[2] 対象コントロールの配列
 * [戻り]
 * true - OK false - NG
 </jp> */
function __da_rangeNumber2()
{
	var facility = __da_rangeNumber2.arguments[0];
	var msg      = __da_rangeNumber2.arguments[1];
	var controls = __da_rangeNumber2.arguments[2];

	if ( controls.length < 2 )
	{
		// No Check
		return true;
	}

	var from = controls[0];
	var to = controls[1];

	if ( from == null )
	{
		// No Check
		return true;
	}

	if ( to == null )
	{
		// No Check
		return true;
	}

	var fromVal = replaceAll(from.value, ".", "");
	fromVal = replaceAll(fromVal, ",", ".");
	var toVal = replaceAll(to.value, ".", "");
	toVal = replaceAll(toVal, ",", ".");

	if ( __da_isInputValue( fromVal ) && __da_isInputValue( toVal ) )
	{
		if ( isNaN( fromVal ) || isNaN( toVal ) )
		{
			// No Check
			return true;
		}

		fromVal = new Number(fromVal);
		toVal = new Number(toVal);

		if ( fromVal <= toVal ) 
		{
			return true;
		}
		else
		{
			__da_setMessage(msg, facility);
			to.focus();
			return false;
		}
	}

	return true;
}

/** <jp>
 * [関数の説明]
 * ダイナミックアクションで実行する文字列範囲チェック。
 * 対象の文字列を16進数に変換して比較します。
 * [引数の説明]
 * arguments[0] メッセージのファシリティ
 * arguments[1] メッセージ
 * arguments[2] 対象コントロールの配列
 * [戻り]
 * true - OK false - NG
 </jp> */
function __da_rangeString()
{
	var facility = __da_rangeString.arguments[0];
	var msg      = __da_rangeString.arguments[1];
	var controls = __da_rangeString.arguments[2];

	if ( controls.length < 2 )
	{
		// No Check
		return true;
	}

	var from = controls[0];
	var to = controls[1];

	if ( from == null )
	{
		// No Check
		return true;
	}

	if ( to == null )
	{
		// No Check
		return true;
	}

	if ( __da_isInputValue( from.value ) && __da_isInputValue( to.value ) )
	{
		if ( from.value.toString(16) <= to.value.toString(16) ) 
		{
			return true;
		}
		else
		{
			__da_setMessage(msg, facility);
			to.focus();
			return false;
		}
	}

	return true;
}

/** <jp>
 * [関数の説明]
 * ダイナミックアクションで実行する関連必須チェック。
 * [引数の説明]
 * arguments[0] メッセージのファシリティ
 * arguments[1] メッセージ
 * arguments[2] 対象コントロールの配列
 * [戻り]
 * true - OK false - NG
 </jp> */
function __da_requriredRelation()
{
	var facility = __da_requriredRelation.arguments[0];
	var msg      = __da_requriredRelation.arguments[1];
	var controls = __da_requriredRelation.arguments[2];

	if ( controls.length < 2 )
	{
		// No Check
		return true;
	}

	var source = controls[0];
	var dist = controls[1];

	if ( source == null )
	{
		// No Check
		return true;
	}

	if ( dist == null )
	{
		// No Check
		return true;
	}

	if ( ( source.type == "text" && __da_isInputValue( source.value ) ) ||
	     ( source.type == "checkbox" && source.checked ) )
	{
		if ( ( dist.type == "text" && !__da_isInputValue( dist.value ) ) ||
		     ( dist.type == "checkbox" && !dist.checked ) )
		{
			__da_setMessage(msg, facility);
			dist.focus();
			return false;
		}
	}

	return true;
}

/** <jp>
 * [関数の説明]
 * ダイナミックアクションで実行するいずれか必須チェック。
 * [引数の説明]
 * arguments[0] メッセージのファシリティ
 * arguments[1] メッセージ
 * arguments[2] 対象コントロールの配列
 * [戻り]
 * true - OK false - NG
 </jp> */
function __da_requriredEither()
{
	var facility = __da_requriredEither.arguments[0];
	var msg      = __da_requriredEither.arguments[1];
	var controls = __da_requriredEither.arguments[2];

	var existsControl = false;
	for ( var i = 0, length = controls.length; i < length; i++ )
	{
		var control = controls[i];

		if ( control == null )
		{
			continue;
		}

		if ( ( control.type == "text" && __da_isInputValue( control.value ) ) ||
		     ( control.type == "checkbox" && control.checked ) )
		{
			return true;
		}
		else
		{
			existsControl = true;
		}
	}

	if ( !existsControl )
	{
		// No Check
		return true;
	}

	__da_setMessage(msg, facility);

	if ( controls.length > 0 )
	{
		if ( controls[0] != null )
		{
			controls[0].focus();
		}
	}

	return false;
}

/** <jp>
 * [関数の説明]
 * 値が入力されているかチェックします。
 * [引数の説明]
 * value 値
 * [戻り]
 * true - 入力 false - 未入力
 </jp> */
function __da_isInputValue(value)
{
	if(!validate(value))
	{
		return false;
	}
	else
	{
		return true;
	}
}

/** <jp>
 * [関数の説明]
 * 「/」を用いた書式の日付時間の範囲チェック。
 * [引数の説明]
 * fromDate   From日付
 * fromTime   From時間
 * toDate     To日付
 * toTime     To時間
 * yearIndex  「/」で分割した際の年のインデックス
 * monthIndex 「/」で分割した際の月のインデックス
 * dayIndex   「/」で分割した際の日のインデックス
 * [戻り]
 * true - 正常範囲 false - 異常範囲
 </jp> */
function __da_dateTimeRangeCheck( fromDate, fromTime, toDate, toTime, yearIndex, monthIndex, dayIndex )
{
	if ( __da_isInputValue( fromDate ) && __da_isInputValue( fromTime ) && __da_isInputValue( toDate ) && __da_isInputValue( toTime ) )
	{
		var splitFromDateValue = fromDate.split(DATE_SEPARATOR);
		var splitFromTimeValue = fromTime.split(TIME_SEPARATOR);

		var splitToDateValue = toDate.split(DATE_SEPARATOR);
		var splitToTimeValue = toTime.split(TIME_SEPARATOR);

		var dateFromValue = new Date( splitFromDateValue[yearIndex], splitFromDateValue[monthIndex] - 1, splitFromDateValue[dayIndex], 
									  splitFromTimeValue[0], splitFromTimeValue[1], splitFromTimeValue[2] );

		var dateToValue = new Date( splitToDateValue[yearIndex], splitToDateValue[monthIndex] - 1, splitToDateValue[dayIndex],
									splitToTimeValue[0], splitToTimeValue[1], splitToTimeValue[2] );

		if ( Date.parse( dateFromValue ) <=  Date.parse( dateToValue ) )
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	return true;
}

/** <jp>
 * [関数の説明]
 * メッセージエリアへメッセージをセットします。
 * [引数の説明]
 * msg      メッセージエリアで表示するメッセージ
 * facility メッセージのファシリティ
 </jp> */
function __da_setMessage(msg, facility)
{
/*  
	if(!document.all.message)return;
	var source = "<img src=\"" + contextPath + "/img/control/message/Attention.gif\" ";

	document.all.message.innerHTML = source + 
			"width=\"15\" height=\"15\" style=\"vertical-align : text-top;\">" +
			"<img src=\"" + contextPath + "/img/control/message/Spacer.gif\" " +
			"width=\"4\"  height=\"19\">" + msg;
*/

	if(!document.all.message)return;

	var errorNoMin = document.all.message.errorNoMin;
	var errorNoMax = document.all.message.errorNoMax;
	var attentionNoMin = document.all.message.attentionNoMin;
	var attentionNoMax = document.all.message.attentionNoMax;
	var warningNoMin = document.all.message.warningNoMin;
	var warningNoMax = document.all.message.warningNoMax;
	var infoNoMin = document.all.message.infoNoMin;
	var infoNoMax = document.all.message.infoNoMax;
	var balloon = document.all.message.balloon;
	var balloonLevel = document.all.message.balloonLevel;
	var mKey = facility;

	var source = "";
	var Css = "";
	var fontCss = "";
	var defineLevel = "";

	if(infoNoMin <= mKey && mKey <= infoNoMax )
	{
		source = "<img src=\"" + contextPath + "/img/control/message/Information.gif\" ";
		Css = "balloon-info";
		fontCss = "balloon-info-font";
		defineLevel = "Info";
	}
	else if(attentionNoMin <= mKey && mKey <= attentionNoMax )
	{
		source = "<img src=\"" + contextPath + "/img/control/message/Attention.gif\" ";
		Css = "balloon-attention";
		fontCss = "balloon-attention-font";
		defineLevel = "Attention";
	}
	else if(warningNoMin <= mKey && mKey <= warningNoMax )
	{
		source = "<img src=\"" + contextPath + "/img/control/message/Warning.gif\" ";
		Css = "balloon-warning";
		fontCss = "balloon-warning-font";
		defineLevel = "Warning";
	}
	else if(errorNoMin <= mKey && mKey <= errorNoMax )
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
