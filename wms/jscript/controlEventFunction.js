//Start Add T.Sumiyama for v5.2 2010/03/12
/**
 * user.jsでテキストボックスのフォーカス時の背景色が指定されていたら指定色を、
 * なければ、デフォルト色を返します。
 */
function defineTextBoxColor()
{
	var textBoxColor = '#CEF7EF';
	if (window._textBoxBgColor == undefined)
	{
		return textBoxColor;
	}
	else
	{
		if (_textBoxBgColor.length > 0)
		{
			textBoxColor = _textBoxBgColor;
		}
		return textBoxColor;
	}
}
//End Add T.Sumiyama for v5.2 2010/03/12

// TextBox ---------------------------------------------------------------------------

 /** <jp>
 * [関数の説明]
 * TextBoxのOnkeydown時の処理、
 * EnterKeyEventを実行するかチェックを行う。
 * [戻り]
 * true 実行する
 * false 実行しない
 </jp> */
function textBoxOnkeydownEnterkey( checkFunction, asyncFlg, element )
{
	if(window.event.keyCode == '13')
	{
		// Add 2008/05/13 v4.3.1.0
		if ( event.altKey )
		{
			return false;
		}
		// End 2008/05/13 v4.3.1.0

		if( checkFunction() )
		{
			if( asyncFlg )
			{
				canEnterkeyFocusMove = false;
				element.blur();
			}
			return true;
		}
	}
	return false;
}

/** <jp>
 * [関数の説明]
 * TextBoxのOnkeydown時の処理、
 * TabkeyEventを実行するかチェックを行う。
 * [戻り]
 * true 実行する
 * false 実行しない
 </jp> */
function textBoxOnkeydownTabkey( checkFunction )
{
	if(window.event.keyCode == '9')
	{
		enterKeyEvent=true;
		var ret = checkFunction();
		if(ret)
		{
			enterKeyEvent=true;
			return true;
		}
	}
	return false;
}

// -----------------------------------------------------------------------------------

// FreeTextBox -----------------------------------------------------------------------

/** <jp>
 * [関数の説明]
 * FreeTextBoxの入力チェックを行う。
 * [戻り]
 * true 入力OK
 * false 入力NG
 </jp> */
function freeTextBoxCheck( id, inputMode, taboo, clientErrInputModeMessage, maxLength, clientErrMaxLengthMessage )
{
	changeBackcolor( id,'#FFFFFF');

	var acceptMultiByteChar = inputMode.indexOf("1") != -1; // 全角文字が入力可能かどうか 
	var noByteCheck = !CLIENT_MULTIBYTE_LENGH_CHECK && acceptMultiByteChar; // application-configでクライアントチェックしないように設定され、かつ全角文字が含まれている場合はバイト数チェックしない
	var doByteCheck = !noByteCheck;
	
	if( !isCharacterCheck( document.all( id ).value, inputMode, taboo ) )
	{
		if ( !enterKeyEvent )
		{
			enterKeyEvent = true;
			alert( clientErrInputModeMessage );
			document.all( id ).focus();
			event.returnValue=false;
			event.cancelBubble=true;
		}
		enterKeyEvent = false;
		return false;
	}
	else if( doByteCheck && !isByteCheck( document.all( id ).value, maxLength ) )
	{
		if ( !enterKeyEvent )
		{
			enterKeyEvent = true;
			alert( clientErrMaxLengthMessage );
			document.all( id ).focus();
			event.returnValue=false;
			event.cancelBubble=true;
		}
		enterKeyEvent = false;
		return false;
	}
	return true;
}

/** <jp>
 * [関数の説明]
 * FreeTextBoxのonfocus時に背景色を変更する。
 </jp> */
function freeTextBoxOnfocus( id )
{
	//Start Add T.Sumiyama for v5.2 2010/03/12
	//色指定をdefinetextBoxColor()に変更します
	changeBackcolor( id, defineTextBoxColor());
	//End Add T.Sumiyama for v5.2 2010/03/12
	if( isFocusSelect )
	{
		document.all( id ).select();
	}
}

/** <jp>
 * [関数の説明]
 * FreeTextBoxのOnkeydown時の処理、
 * AutoCompleteでエンターキーが押下された時AutoCompleteItemClickを呼び出す。
 </jp> */
function freeTextBoxOnkeydownAutoComplete( checkFunction, listId )
{
	if(window.event.keyCode == '13')
	{
		// Add 2008/05/14 v4.3.1.0
		if ( event.altKey )
		{
			return false;
		}
		// End 2008/05/14 v4.3.1.0

		if( checkFunction() )
		{
			if( isActiveList(listId) )
			{
				if( !enterList(listId) )
				{
					delList(listId);
					clearKeyCode();
				}
				clearKeyCode();
			}
			else
			{
				delList(listId);
				clearKeyCode();
 			}
			event.cancelBubble=true;
		}
	}
}

/** <jp>
 * [関数の説明]
 * FreeTextBoxのOnkeydown時の処理、
 * EnterkeyEventを実行するかチェックを行う。
 * [戻り]
 * true 実行する
 * false 実行しない
 </jp> */
function freeTextBoxOnkeydownEnterkey( checkFunction )
{
	if(window.event.keyCode == '13')
	{
		// Add 2008/05/13 v4.3.1.0
		if ( event.altKey )
		{
			return false;
		}
		// End 2008/05/13 v4.3.1.0

		if( checkFunction() )
		{
			event.cancelBubble=true;
			return true;
		}
	}
	return false;
}

/** <jp>
 * [関数の説明]
 * FreeTextBoxのOnkeyup時の処理、
 * InputComplateEventがtrueの場合、最大文字数まで文字を入力した時にInputCompleteイベントを呼び出す。
 </jp> */


/** <jp>
 * [関数の説明]
 * FreeTextBoxのOnkeyup時の処理、
 * InputCompleteEventを実行するかチェックを行う。
 * [戻り]
 * true 実行する
 * false 実行しない
 </jp> */
function freeTextBoxOnkeyupInputComplete( value, maxLength, checkFunction )
{
	if( getByte(value) >= maxLength )
	{
		if( checkFunction() )
		{
			return true;
		}
	}
	return false;
}

/** <jp>
 * [関数の説明]
 * FreeTextBoxのOnkeyup時の処理、
 * autoCompleleEventがtrueの場合、文字入力時にAutoCompleteイベントを呼び出す。
 </jp> */
function freeTextBoxOnkeyupAutoComplete( checkFunction, id, eventAutoComplate, listId )
{
	if ( isEffective( event.keyCode ) && xmlhttpRequest == null)
	{
		if( checkFunction() )
		{
			isFocusSelect=false;
			autoCompleteEvent( id, eventAutoComplate, listId );
		}
	}
}

// -----------------------------------------------------------------------------------

// NumberTextBox ---------------------------------------------------------------------
/** <jp>
 * [関数の説明]
 * NumberTextBoxの入力チェックを行う。
 * [戻り]
 * true 入力OK
 * false 入力NG
 </jp> */
function numberTextBoxCheck
	( id, numDecimalLength, clientErrInputModeMessage, length, decimalLength, clientErrLengthMessage, rangeChkFlg, minRange, maxRange, clientErrRangeMessage )
{
	changeBackcolor( id,'#FFFFFF');

	if( !isNumberTextBoxCheck( document.all(id).value , numDecimalLength ))
	{
		if ( !enterKeyEvent )
		{
			enterKeyEvent = true;
			alert( clientErrInputModeMessage );
			document.all(id).focus();
			event.returnValue=false;
			event.cancelBubble=true;
		}
		enterKeyEvent = false;
		return false;
	}
	else if( !isNumberOfDigitCheck( document.all(id).value, length, decimalLength ) )
	{
		if ( !enterKeyEvent )
		{
			enterKeyEvent = true;
			alert(clientErrLengthMessage);
			document.all(id).focus();
			event.returnValue=false;
			event.cancelBubble=true;
		}
		enterKeyEvent = false;
		return false;
	}
	else if ( rangeChkFlg && !isNumberCheckRange( document.all(id).value, minRange, maxRange ) )
	{
		if ( !enterKeyEvent )
		{
			enterKeyEvent = true;
			alert(clientErrRangeMessage);
			document.all(id).focus();
			event.returnValue=false;
			event.cancelBubble=true;
		}
		enterKeyEvent = false;
		return false;
	}

	return true;
}

/** <jp>
 * [関数の説明]
 * NumberTextBoxのonfocusの処理を行う。
 * 背景色の変更、入力値から「,」を取り除く、入力値を選択状態にする。
 </jp> */
//v5.2.4.0 2013/1/17 カンマフォーマット対応でカンマとピリオドを変数に置き換えた
function numberTextBoxOnfocus( id )
{
	//Start Add T.Sumiyama for v5.2 2010/03/12
	//色指定をdefinetextBoxColor()に変更します
	changeBackcolor( id, defineTextBoxColor());
	//End Add T.Sumiyama for v5.2 2010/03/12

	document.all(id).value = getString(document.all(id).value, __COMMA_CHAR);

	if( isFocusSelect )
	{
		document.all(id).select();
	}
}

// -----------------------------------------------------------------------------------

// FormatTextBox ---------------------------------------------------------------------
/** <jp>
 * [関数の説明]
 * FormatTextBoxの入力チェックを行う。
 * [戻り]
 * true 入力OK
 * false 入力NG
 </jp> */
function formatTextBoxCheck( id, format, clientErrFormat, length )
{
	changeBackcolor(id,'#FFFFFF');

	if( !isFormatCheck( document.all(id).value, format, '-' ) )
	{
		if ( !enterKeyEvent )
		{
			enterKeyEvent = true;
			alert( clientErrFormat );
			document.all(id).focus();
			event.returnValue=false;
			event.cancelBubble=true;
		}
		enterKeyEvent = false;
		return false;
	}

	return true;
}

/** <jp>
 * [関数の説明]
 * FormatTextBoxのonfocusの処理を行う。
 * 背景色の変更、入力値から「-」を取り除く、入力値を選択状態にする。
 </jp> */
function formatTextBoxOnfocus( id )
{
	//Start Add T.Sumiyama for v5.2 2010/03/12
	//色指定をdefinetextBoxColor()に変更します
	changeBackcolor( id, defineTextBoxColor());
	//End Add T.Sumiyama for v5.2 2010/03/12
	document.all(id).value = getString(document.all(id).value, '-');

	if ( isFocusSelect )
	{
		document.all(id).select();
	}
}

// -----------------------------------------------------------------------------------

// DateTextBox -----------------------------------------------------------------------
/** <jp>
 * [関数の説明]
 * DateTextBoxの入力チェックを行う。
 * [戻り]
 * true 入力OK
 * false 入力NG
 </jp> */
function dateTextBoxCheck( id, clientErrFormat, inputFormatLength, clientErrDate, inputFormat )
{
	changeBackcolor(id,'#FFFFFF');

	if( !isNumberCheck( document.all(id).value ) )
	{
		if ( !enterKeyEvent )
		{
			enterKeyEvent = true;
			alert( clientErrFormat );
			document.all(id).focus();
			event.returnValue=false;
			event.cancelBubble=true;
		}
		enterKeyEvent = false;
		return false;
	}
	else if( getByte( document.all(id).value ) > 0 && getByte( document.all(id).value ) != inputFormatLength )
	{
		if ( !enterKeyEvent )
		{
			enterKeyEvent = true;
			alert( clientErrDate );
			document.all(id).focus();
			event.returnValue=false;
			event.cancelBubble=true;
		}
		enterKeyEvent = false;
		return false;
	}
	else if( !isDateCheck( document.all(id).value, inputFormat ) )
	{
		if ( !enterKeyEvent )
		{
			enterKeyEvent = true;
			alert( clientErrDate );
			document.all(id).focus();
			event.returnValue=false;
			event.cancelBubble=true;
		}
		enterKeyEvent = false;
		return false;
	}

	return true;
}

/** <jp>
 * [関数の説明]
 * DateTextBoxのonfocusの処理を行う。
 * 背景色の変更、入力値をinputFormat、入力値を選択状態にする。
 </jp> */
function dateTextBoxOnfocus( id, viewFormat, inputFormat, separator )
{
	//Start Add T.Sumiyama for v5.2 2010/03/12
	//色指定をdefinetextBoxColor()に変更します
	changeBackcolor( id, defineTextBoxColor());
	//End Add T.Sumiyama for v5.2 2010/03/12
	document.all(id).value = changeDateFormat( document.all(id).value, viewFormat, inputFormat, separator );

	if ( isFocusSelect )
	{
		document.all(id).select();
	}
}


// -----------------------------------------------------------------------------------

// TimeTextBox -----------------------------------------------------------------------
/** <jp>
 * [関数の説明]
 * TimeTextBoxの入力チェックを行う。
 * [戻り]
 * true 入力OK
 * false 入力NG
 </jp> */
function timeTextBoxCheck( id, clientErrFormat, inputFormatLength, clientErrTime, inputFormat )
{
	changeBackcolor(id,'#FFFFFF');

	if( !isNumberCheck( document.all(id).value ) )
	{
		if ( !enterKeyEvent )
		{
			enterKeyEvent = true;
			alert( clientErrFormat );
			document.all(id).focus();
			event.returnValue=false;
			event.cancelBubble=true;
		}
		enterKeyEvent = false;
		return false;
	}
	else if( getByte( document.all(id).value ) > 0 && getByte( document.all(id).value ) != inputFormatLength )
	{
		if ( !enterKeyEvent )
		{
			enterKeyEvent = true;
			alert( clientErrTime );
			document.all(id).focus();
			event.returnValue=false;
			event.cancelBubble=true;
		}
		enterKeyEvent = false;
		return false;
	}
	else if( !isTimeCheck( document.all(id).value, inputFormat ) )
	{
		if ( !enterKeyEvent )
		{
			enterKeyEvent = true;
			alert( clientErrTime );
			document.all(id).focus();
			event.returnValue=false;
			event.cancelBubble=true;
		}
		enterKeyEvent = false;
		return false;
	}

	return true;
}

/** <jp>
 * [関数の説明]
 * TimeTextBoxのonfocusの処理を行う。
 * 背景色の変更、入力値をinputFormat、入力値を選択状態にする。
 </jp> */
function timeTextBoxOnfocus( id, viewFormat, inputFormat, separator )
{
	//Start Add T.Sumiyama for v5.2 2010/03/12
	//色指定をdefinetextBoxColor()に変更します
	changeBackcolor( id, defineTextBoxColor());
	//End Add T.Sumiyama for v5.2 2010/03/12
	document.all(id).value = changeTimeFormat( document.all(id).value, viewFormat, inputFormat, separator );

	if ( isFocusSelect )
	{
		document.all(id).select();
	}
}

// -----------------------------------------------------------------------------------

// BarCodeTextBox --------------------------------------------------------------------
/** <jp>
 * [関数の説明]
 * BarCodeTextBoxの入力チェックを行う。
 * [戻り]
 * true 入力OK
 * false 入力NG
 </jp> */
function barCodeTextBoxCheck( id, clientErrInputMode, maxLength, clientErrMaxLength )
{
	changeBackcolor(id,'#FFFFFF');

	if( !isBarCodeTextBoxCheck(document.all(id).value ))
	{
		if ( !enterKeyEvent )
		{
			enterKeyEvent = true;
			alert( clientErrInputMode );
			document.all(id).focus();
			event.returnValue=false; event.cancelBubble=true;
		}
		enterKeyEvent = false;
		return false;
	}
	else if( !isByteCheck( document.all(id).value, maxLength ) )
	{
		if ( !enterKeyEvent )
		{
			enterKeyEvent = true;
			alert( clientErrMaxLength );
			document.all(id).focus();
			event.returnValue=false;
			event.cancelBubble=true;
		}
		enterKeyEvent = false;
		return false;
	}

	return true;
}

/** <jp>
 * [関数の説明]
 * BarCodeTextBoxのonfocusの処理を行う。
 * 背景色の変更、入力値を選択状態にする。
 </jp> */
function barCodeTextBoxOnfocus( id )
{
	//Start Add T.Sumiyama for v5.2 2010/03/12
	//色指定をdefinetextBoxColor()に変更します
	changeBackcolor( id, defineTextBoxColor());
	//End Add T.Sumiyama for v5.2 2010/03/12
	if ( isFocusSelect )
	{
		document.all(id).select();
	}
}

/** <jp>
 * [関数の説明]
 * BarCodeTextBoxのOnkeydown時の処理、
 * EnterKeyEventを実行するかチェックを行う。
 </jp> */
function barCodeTextBoxOnkeydownEnterkey( checkFunction )
{
	if(window.event.keyCode == '13')
	{
		// Add 2008/05/13 v4.3.1.0
		if ( event.altKey )
		{
			return false;
		}
		// End 2008/05/13 v4.3.1.0

		if( checkFunction() )
		{
			event.cancelBubble=true;
		}
		return true;
	}
	return false;
}

/** <jp>
 * [関数の説明]
 * BarCodeTextBoxのOnkeyup時、
 * InputCompleteEventがfalseの場合の処理、
 </jp> */
function barCodeTextBoxOnkeyupInputCompleteFalse( id, maxLength, startPosition, endPosition )
{
	if( getByte(document.all(id).value) >= maxLength )
	{
		document.all(id).value = getSubString( document.all(id).value, startPosition, endPosition );
		document.all(id).focus();
	}
}

/** <jp>
 * [関数の説明]
 * BarCodeTextBoxのOnkeyup時、
 * InputCompleteEventがtrueの場合の処理、
 </jp> */
function barCodeTextBoxOnkeyupInputCompleteTrue( id, maxLength, checkFunction )
{
	if( getByte(document.all(id).value) >= maxLength )
	{
		if( checkFunction() )
		{
			return true;
		}
	}
	return false;
}

// -----------------------------------------------------------------------------------

// PasswordTextBox -------------------------------------------------------------------
/** <jp>
 * [関数の説明]
 * PasswordTextBoxのonblurの処理を行う。
 * 背景色の変更。
 </jp> */
function passwordTextBoxOnblur( id )
{
	changeBackcolor( id, '#FFFFFF' );
}

/** <jp>
 * [関数の説明]
 * PasswordTextBoxのonfocusの処理を行う。
 * 背景色の変更を行う。
 </jp> */
function passwordTextBoxOnfocus( id )
{
	//Start Add T.Sumiyama for v5.2 2010/03/12
	//色指定をdefinetextBoxColor()に変更します
	changeBackcolor( id, defineTextBoxColor());
	//End Add T.Sumiyama for v5.2 2010/03/12
	document.all( id ).select();
}

/** <jp>
 * [関数の説明]
 * PasswordTextBoxのOnkeydown時の処理、
 * EnterKeyEventを実行するかチェックを行う。
 </jp> */
function passwordTextBoxOnkeydownEnterkey()
{
	if(window.event.keyCode == '13')
	{
		// Add 2008/05/13 v4.3.1.0
		if ( event.altKey )
		{
			return false;
		}
		// End 2008/05/13 v4.3.1.0

		return true;
	}
	return false;
}

/** <jp>
 * [関数の説明]
 * PasswordTextBoxのOnkeydown時の処理、
 * TabkeyEventを実行するかチェックを行う。
 </jp> */
function passwordTextBoxOnkeydownTabkey()
{
	if(window.event.keyCode == '9')
	{
		return true;
	}
	return false;
}
// -----------------------------------------------------------------------------------

// TextArea --------------------------------------------------------------------------
/** <jp>
 * [関数の説明]
 * TextAreaの入力チェックを行う。
 * [戻り]
 * true 入力OK
 * false 入力NG
 </jp> */
function textAreaCheck( id, taboo, clientErrTabooStr, maxLength, clientErrMaxLength )
{
	changeBackcolor( id, '#FFFFFF' );

	var doByteCheck = CLIENT_MULTIBYTE_LENGH_CHECK;
	
	if( isProhibitedCharacter( document.all( id ).value, taboo ) )
	{
		if ( !enterKeyEvent )
		{
			enterKeyEvent = true;
			alert( clientErrTabooStr );
			document.all( id ).focus();
		}

		enterKeyEvent = false;
		return false;
	}
	else if( doByteCheck && !isByteCheck( document.all( id ).value, maxLength ) )
	{
		if ( !enterKeyEvent )
		{
			enterKeyEvent = true;
			alert( clientErrMaxLength );
			document.all( id ).focus();
		}

		enterKeyEvent = false;
		return false;
	}

	return true;
}

/** <jp>
 * [関数の説明]
 * TextAreaのonfocusの処理を行う。
 * 背景色の変更、入力値を選択状態にする。
 </jp> */
function textAreaOnfocus( id )
{
	//Start Add T.Sumiyama for v5.2 2010/03/12
	//色指定をdefinetextBoxColor()に変更します
	changeBackcolor( id, defineTextBoxColor());
	//End Add T.Sumiyama for v5.2 2010/03/12
	document.all( id ).select();
}

/** <jp>
 * [関数の説明]
 * TextAreaのOnkeydown時の処理
 </jp> */
function textAreaOnkeydownEnterkey()
{
	if(  event.keyCode == 13 )
	{
		// Add 2008/05/13 v4.3.1.0
		if ( event.altKey )
		{
			return false;
		}
		// End 2008/05/13 v4.3.1.0

		event.cancelBubble = true;
	}
}
