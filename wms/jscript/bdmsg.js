var bdmsg = "";
var timerID;

var ID = "id";
var BDMSG_TAG = "div";
var BDMSG_ID = "__BDMSG_";
var BDMSG_COUNTER_ID = "xmlHttpRequestBDMSGCounter";

var BDMSG_WIDTH         = 200;
var BDMSG_MAXCOUNT      = 10;
var BDMSG_LOADCOUNT     = 8;
var BDMSG_HEIGHT_OFFSET = 38;
var BDMSG_WIDTH_OFFSET  = 20;
var BDMSG_CREATE_TIMER  = 500;
var BDMSG_ENDWAIT_TIMER = 300;

var BDMSG_BGCOLOR       = "#FAFAD2";
var BDMSG_BORDER        = "ridge #FAFAD2 3px";
var BDMSG_PADDING       = "5px";

var FONT_START_TAG      = "<font class='";
var FONT_START_TAG2     = "'>";
var FONT_END_TAG        = "</font>";

var SIZE_SUFFIX = "px";

var BDMSG_WAIT = 30 * 1000;

/** 
 * メッセージの表示
 *
 * @param msg 表示するメッセージ
 */
function bdMsg( msg, balloonClass, fontClass )
{
	// setTimeout()のスタックをキャンセルし、バルーンが表示されている場合は、除去します。
	bdmsg = document.createElement( BDMSG_TAG );
	bdmsg.setAttribute( ID, BDMSG_ID );
	document.body.appendChild( bdmsg );
	var scrollTop  = parseInt( document.body.scrollTop );
	var scrollLeft = parseInt( document.body.scrollLeft );
// 2009/12/02 sumiyama add (IE8対応)
	if(scrollTop === 0 && scrollLeft === 0)
	{
		scrollTop  = parseInt( document.documentElement.scrollTop );
		scrollLeft = parseInt( document.documentElement.scrollLeft );
	}
// add End.

	document.all( BDMSG_ID ).innerHTML = FONT_START_TAG + fontClass + FONT_START_TAG2 + msg + FONT_END_TAG;
	document.all( BDMSG_ID ).style.position = 'absolute';
//	document.all( BDMSG_ID ).style.top  = ( document.body.clientHeight / 2 ) - document.all( BDMSG_ID ).offsetHeight + scrollTop;
//	document.all( BDMSG_ID ).style.left = ( document.body.clientWidth / 2 )  - ( document.all( BDMSG_ID ).offsetWidth / 2 ) + scrollLeft;
//Add 2008/02/28 v4.3
	document.all( BDMSG_ID ).style.top  = 25 + scrollTop;
	document.all( BDMSG_ID ).style.left = 7 + scrollLeft;
//Edd 2008/02/28 v4.3
	document.all( BDMSG_ID ).onclick = removebdMsg;
	document.all( BDMSG_ID ).className = balloonClass;

	hiddenSelect( document.all( BDMSG_ID ) );

	new Effect.Appear(bdmsg,{duration:0.15});

	timerID = setTimeout( "removebdMsg()", BDMSG_WAIT );
}

/** 
 * 演出ありのメッセージの非表示化
 *
 */
function effectRemove()
{
	try
	{
		new Effect.Puff( document.all( BDMSG_ID ) );
		setTimeout( "removebdMsg()", 1 * 1000 );
	}
	catch(ex)
	{
		removebdMsg();
	}
}

/** 
 * メッセージの非表示化
 *
 */
function removebdMsg()
{
	visibleSelect( document.all( BDMSG_ID ) );
	bdmsg = null;
	if( document.all( BDMSG_ID ) != null )
	{
		document.body.removeChild( document.all( BDMSG_ID ) );
	}
}

/** 
 * 現在表示中のメッセージを除去します。
 *
 */
function clearExistingBdMsg()
{
	if( timerID != undefined )
	{
		clearTimeout( timerID );
		removebdMsg();
	}
}
