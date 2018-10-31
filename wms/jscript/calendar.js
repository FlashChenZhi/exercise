var turnOffYearSpan = false;     // true = Only show This Year and Next, false = show +/- 5 years
var weekStartsOnSunday = true;  // true = Start the week on Sunday, false = start the week on Monday
var showWeekNumber = false;  // true = show week number,  false = do not show week number

var languageCode = 'en';	// Possible values: 	en,ge,no,nl,es,pt-br,fr
var calendar_display_time = true;
var todayStringFormat = '[todayString] [UCFdayString]. [day]. [monthString] [year]';

// BlueDOG
var pathToImages = getContextPath() + "img/control/calendar/";	// Relative to your HTML file
function getContextPath() {
	var path = "./";
	var span = document.createElement("span");
	span.innerHTML = "<a href = \"" + path + "\" />";
	var url = span.firstChild.href;
	var urlArray = url.split('/');
	// [http:], [] , [localhost:8090], [wms], ...の順にパスが入る
	return '/'+ urlArray[3] + '/';
}

var speedOfSelectBoxSliding = 200;	// Milliseconds between changing year and hour when holding mouse over "-" and "+" - lower value = faster
var intervalSelectBox_minutes = 5;	// Minute select box - interval between each option (5 = default)

var calendar_offsetTop = 0;		// Offset - calendar placement - You probably have to modify this value if you're not using a strict doctype
var calendar_offsetLeft = 0;	// Offset - calendar placement - You probably have to modify this value if you're not using a strict doctype
var calendarDiv = false;

var MSIE = false;
var Opera = false;
if(navigator.userAgent.indexOf('MSIE') >= 0 && navigator.userAgent.indexOf('Opera') < 0)MSIE = true;
if(navigator.userAgent.indexOf('Opera') >= 0)Opera = true;

var daysInMonthArray = [31,28,31,30,31,30,31,31,30,31,30,31];

var currentMonth;
var currentYear;

var currentHour;
var currentMinute;

var calendarContentDiv;

var returnDateTo;
var returnFormat;

var activeSelectBoxMonth;
var activeSelectBoxYear;
var activeSelectBoxHour;
var activeSelectBoxMinute;

var nextMonth;
var nextYear;
var calendarNextDiv;

var monthArray;
var monthArrayShort;
var dayArray;
var weekString;
var todayString = '';

var calDivWidth = "200";
var calendarBordr = 5;
var calFilter = false;


// BlueDOG
function calendarPrepare(lang)
{
	// 言語情報によりフォーマットを初期化
	switch(lang)
	{
		case "ja":	/* Japanese */
			// Start Mod T.Sumiyama for v5.2 文字列の修正
			monthArray = ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'];
			monthArrayShort = ['1 月','2 月','3 月','4 月','5 月','6 月','7 月','8 月','9 月','10 月','11 月','12 月'];
			dayArray = ['月','火','水','木','金','土','日'];
			weekString = '曜日';
			todayString = '';
			todayStringFormat='[year] [monthString][day] [todayString] [UCFdayString]';
			languageCode = 'ja';
			break;
		default:
			monthArray = ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'];
			monthArrayShort = ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'];
			dayArray = ['Mon','Tue','Wed','Thu','Fri','Sat','Sun'];
			weekString = 'Week';
			todayString = '';
			todayStringFormat='[year].[monthString].[day].[todayString] [UCFdayString]';
			break;
	}
	if (weekStartsOnSunday)
	{
	   var tempDayName = dayArray[6];
	   for(var theIx = 6; theIx > 0; theIx--)
	   {
		  dayArray[theIx] = dayArray[theIx - 1];
	   }
	   dayArray[0] = tempDayName;
	}
}

var iframeObj = false;
//// fix for EI frame problem on time dropdowns 09/30/2006
var iframeObj2 = false;
function EIS_FIX_EI1(where2fixit)
{
	if(!iframeObj2)return;
	iframeObj2.style.display = 'block';
	iframeObj2.style.height = $(where2fixit).offsetHeight + 1;
	iframeObj2.style.width = $(where2fixit).offsetWidth;
	iframeObj2.style.left = getleftPos($(where2fixit)) - $(where2fixit).offsetWidth;
	iframeObj2.style.top = getTopPos($(where2fixit));
}

function EIS_Hide_Frame()
{
	if(iframeObj2)iframeObj2.style.display = 'none';
}
//// fix for EI frame problem on time dropdowns 09/30/2006
var returnDateToYear;
var returnDateToMonth;
var returnDateToDay;
var returnDateToHour;
var returnDateToMinute;
var inputYear;
var inputMonth;
var inputDay;
var inputHour;
var inputMinute;
var calendarDisplayTime = false;

var selectBoxHighlightColor = '#D60808'; // Highlight color of select boxes
var selectBoxRolloverBgColor = '#f5f5ff'; // Background color on drop down lists(rollover)

var selectBoxMovementInProgress = false;
var activeSelectBox = false;

function cancelCalendarEvent()
{
	return false;
}
function isLeapYear(inputYear)
{
	if(inputYear % 400 == 0 || (inputYear % 4 == 0 && inputYear % 100 != 0)) return true;
	return false;
}

var activeSelectBoxMonth = false;
var activeSelectBoxDirection = false;

function highlightMonthYear()
{
	if(activeSelectBoxMonth)activeSelectBoxMonth.className = '';
	activeSelectBox = this;

	if(this.className == 'monthYearActive')
	{
		this.className = '';
	}
	else
	{
		this.className = 'monthYearActive';
		activeSelectBoxMonth = this;
	}

	if(this.innerHTML.indexOf('-') >= 0 || this.innerHTML.indexOf('+') >= 0)
	{
		if(this.className == 'monthYearActive') selectBoxMovementInProgress = true;
		else selectBoxMovementInProgress = false;

		if(this.innerHTML.indexOf('-') >= 0) activeSelectBoxDirection = -1;
		else activeSelectBoxDirection = 1;
	}
	else selectBoxMovementInProgress = false;

}

function showMonthDropDown()
{
	if($('monthDropDown').style.display == 'block')
	{
		$('monthDropDown').style.display = 'none';
		//// fix for EI frame problem on time dropdowns 09/30/2006
		EIS_Hide_Frame();
	}
	else
	{
		$('monthDropDown').style.display = 'block';
		$('yearDropDown').style.display = 'none';
		if (MSIE)
		{
			EIS_FIX_EI1('monthDropDown');
		}
		//// fix for EI frame problem on time dropdowns 09/30/2006
	}
}

function showYearDropDown()
{
	if($('yearDropDown').style.display == 'block')
	{
		$('yearDropDown').style.display = 'none';
		//// fix for EI frame problem on time dropdowns 09/30/2006
		EIS_Hide_Frame();
	}
	else
	{
		$('yearDropDown').style.display = 'block';
		$('monthDropDown').style.display = 'none';
		if (MSIE)
		{
			EIS_FIX_EI1('yearDropDown');
		}
	}
}
function showHourDropDown()
{
	if($('hourDropDown').style.display == 'block')
	{
		$('hourDropDown').style.display = 'none';
		EIS_Hide_Frame();
	}
	else
	{
		$('hourDropDown').style.display = 'block';
		$('monthDropDown').style.display = 'none';
		$('yearDropDown').style.display = 'none';
		$('minuteDropDown').style.display = 'none';
		if (MSIE)
		{
			EIS_FIX_EI1('hourDropDown');
		}
	}
}
function showMinuteDropDown()
{
	if($('minuteDropDown').style.display == 'block')
	{
		$('minuteDropDown').style.display = 'none';
		EIS_Hide_Frame();
	}
	else
	{
		$('minuteDropDown').style.display='block';
		$('monthDropDown').style.display='none';
		$('yearDropDown').style.display='none';
		$('hourDropDown').style.display='none';
		if (MSIE)
		{
			EIS_FIX_EI1('minuteDropDown');
		}
	}
}

function selectMonth()
{
	$('calendar_month_txt').innerHTML = this.innerHTML;
	currentMonth = this.id.replace(/[^\d]/g,'');
	$('monthDropDown').style.display = 'none';
	//// fix for EI frame problem on time dropdowns 09/30/2006
	EIS_Hide_Frame();
	for(var no = 0; no < monthArray.length; no++)
	{
		$('monthDiv_'+no).style.color = '';
	}
	this.style.color = selectBoxHighlightColor;
	activeSelectBoxMonth = this;
	writeCalendarContent();
}

function selectYear()
{
	$('calendar_year_txt').innerHTML = this.innerHTML;
	currentYear = this.innerHTML.replace(/[^\d]/g,'');
	$('yearDropDown').style.display = 'none';
	//// fix for EI frame problem on time dropdowns 09/30/2006
	EIS_Hide_Frame();
	if(activeSelectBoxYear)
	{
		activeSelectBoxYear.style.color='';
	}
	activeSelectBoxYear = this;
	this.style.color = selectBoxHighlightColor;
	writeCalendarContent();
}

function switchMonth()
{
	if(this.src.indexOf('left') >= 0)
	{
		currentMonth = currentMonth - 1;
		if(currentMonth < 0)
		{
			currentMonth = 11;
			currentYear = currentYear - 1;
		}
	}
	else
	{
		currentMonth = currentMonth + 1;
		if(currentMonth > 11)
		{
			currentMonth = 0;
			currentYear = currentYear / 1 + 1;
		}
	}
	writeCalendarContent();
}

/**
 * Month の down menuを生成します。
 * div = menu部分の枠
 * subDiv = 各月を表示するためのdiv
 */
function createMonthDiv()
{
	var div = document.createElement('DIV');
	div.className='monthYearPicker';
	div.id = 'monthPicker';
	div.style.textAlign = "center";

	// monthArray=1月～12月
	for(var no = 0; no < monthArray.length; no++)
	{
		var subDiv = document.createElement('DIV');
		subDiv.innerHTML = monthArray[no];
		subDiv.onmouseover = highlightMonthYear;
		subDiv.onmouseout = highlightMonthYear;
		subDiv.onclick = selectMonth;
		subDiv.id = 'monthDiv_' + no;
		subDiv.onselectstart = cancelCalendarEvent;
		subDiv.style.backgroundColor = (no % 2 == 0)?"#B0ADD3" :"#DAD9EE" ;

		div.appendChild(subDiv);

		if(currentMonth && currentMonth == no)
		{
			subDiv.style.color = selectBoxHighlightColor;
			activeSelectBoxMonth = subDiv;
		}
	}
	return div;
}

function changeSelectBoxYear(e,inputObj)
{
	if(!inputObj)inputObj = this;
	var yearItems = inputObj.parentNode.getElementsByTagName('DIV');
	if(inputObj.innerHTML.indexOf('-') >= 0)
	{
		var startYear = yearItems[1].innerHTML / 1 - 1;
		if(activeSelectBoxYear)
		{
			activeSelectBoxYear.style.color = '';
		}
	}
	else
	{
		var startYear = yearItems[1].innerHTML / 1 + 1;
		if(activeSelectBoxYear)
		{
			activeSelectBoxYear.style.color = '';
		}
	}
	for(var no = 1; no < yearItems.length - 1; no++)
	{
		yearItems[no].innerHTML = startYear + no - 1;
		yearItems[no].id = 'yearDiv' + (startYear / 1 + no / 1 - 1);

	}
	if(activeSelectBoxYear)
	{
		activeSelectBoxYear.style.color = '';
		if($('yearDiv' + currentYear))
		{
			activeSelectBoxYear = $('yearDiv' + currentYear);
			activeSelectBoxYear.style.color = selectBoxHighlightColor;;
		}
	}
}

function updateYearDiv()
{
    var yearSpan = 5;
    if (turnOffYearSpan)
    {
       yearSpan = 0;
    }
	var div = $('yearDropDown');
	var yearItems = div.getElementsByTagName('DIV');
	for(var no = 1; no < yearItems.length - 1; no++)
	{
		yearItems[no].innerHTML = currentYear / 1 - yearSpan + no;
		if(currentYear == (currentYear / 1 -yearSpan + no))
		{
			yearItems[no].style.color = selectBoxHighlightColor;
			activeSelectBoxYear = yearItems[no];
		}
		else
		{
			yearItems[no].style.color = '';
		}
	}
}

function updateMonthDiv()
{
	for(no = 0; no < 12; no++)
	{
		$('monthDiv_' + no).style.color = '';
	}
	$('monthDiv_' + currentMonth).style.color = selectBoxHighlightColor;
	activeSelectBoxMonth = 	$('monthDiv_' + currentMonth);
}

function createYearDiv()
{
	if(!$('yearDropDown'))
	{
		var div = document.createElement('DIV');
		div.className='monthYearPicker';
		div.style.textAlign = "center";
	}
	else
	{
		var div = $('yearDropDown');
		var subDivs = div.getElementsByTagName('DIV');
		for(var no = 0; no < subDivs.length; no++)
		{
			subDivs[no].parentNode.removeChild(subDivs[no]);
		}
	}

	var d = new Date();
	if(currentYear)
	{
		d.setFullYear(currentYear);
	}

	var startYear = d.getFullYear()/1 - 5;

    var yearSpan = 10;
	if (! turnOffYearSpan)
	{
		var subDiv = document.createElement('DIV');
		subDiv.innerHTML = '&nbsp;&nbsp;- ';
		subDiv.onclick = changeSelectBoxYear;
		subDiv.onmouseover = highlightMonthYear;
		subDiv.onmouseout = function(){ selectBoxMovementInProgress = false;};
		subDiv.onselectstart = cancelCalendarEvent;
		div.appendChild(subDiv);
    }
	else
	{
       startYear = d.getFullYear()/1 - 0;
       yearSpan = 2;
    }

	for(var no = startYear; no < (startYear + yearSpan); no++)
	{
		var subDiv = document.createElement('DIV');
		subDiv.innerHTML = no;
		subDiv.onmouseover = highlightMonthYear;
		subDiv.onmouseout = highlightMonthYear;
		subDiv.onclick = selectYear;
		subDiv.id = 'yearDiv' + no;
		subDiv.onselectstart = cancelCalendarEvent;

		if (no % 2 == 0)
		{
			subDiv.style.backgroundColor = "#B0ADD3";
		}
		else
		{
			subDiv.style.backgroundColor = "#DAD9EE";
		}

		div.appendChild(subDiv);
		if(currentYear && currentYear == no)
		{
			subDiv.style.color = selectBoxHighlightColor;
			activeSelectBoxYear = subDiv;
		}
	}
	if (! turnOffYearSpan)
	{
    	var subDiv = document.createElement('DIV');
    	subDiv.innerHTML = '&nbsp;&nbsp;+ ';
    	subDiv.onclick = changeSelectBoxYear;
    	subDiv.onmouseover = highlightMonthYear;
    	subDiv.onmouseout = function(){ selectBoxMovementInProgress = false;};
    	subDiv.onselectstart = cancelCalendarEvent;
    	div.appendChild(subDiv);
	}
	return div;
}

/* This function creates the hour div at the bottom bar */

function slideCalendarSelectBox()
{
	if(selectBoxMovementInProgress)
	{
		if(activeSelectBox.parentNode.id == 'yearDropDown')
		{
			changeSelectBoxYear(false,activeSelectBox);
		}
	}
	setTimeout('slideCalendarSelectBox()',speedOfSelectBoxSliding);
}

function highlightSelect()
{
	if(this.className == 'selectBoxTime')
	{
		this.className = 'selectBoxTimeOver';
		this.getElementsByTagName('IMG')[0].src = pathToImages + 'down_time_over.gif';
	}
	else if(this.className == 'selectBoxTimeOver')
	{
		this.className = 'selectBoxTime';
		this.getElementsByTagName('IMG')[0].src = pathToImages + 'down_time.gif';
	}

	if(this.className == 'selectBox')
	{
		this.className = 'selectBoxOver';
		this.getElementsByTagName('IMG')[0].src = pathToImages + 'down_over.gif';
	}
	else if(this.className == 'selectBoxOver')
	{
		this.className = 'selectBox';
		this.getElementsByTagName('IMG')[0].src = pathToImages + 'down.gif';
	}
}

function highlightArrow()
{
	if(this.src.indexOf('over') >= 0)
	{
		if(this.src.indexOf('left') >= 0)this.src = pathToImages + 'left.gif';
		if(this.src.indexOf('right') >= 0)this.src = pathToImages + 'right.gif';
	}
	else
	{
		if(this.src.indexOf('left') >= 0)this.src = pathToImages + 'left_over.gif';
		if(this.src.indexOf('right') >= 0)this.src = pathToImages + 'right_over.gif';
	}
}

function highlightClose()
{
	if(this.src.indexOf('over') >= 0)
	{
		this.src = pathToImages + 'close.gif';
	}
	else
	{
		this.src = pathToImages + 'close_over.gif';
	}
}

function closeCalendar()
{
//	if (calendarDiv)
	calendarDiv.style.display='none';
	if(iframeObj)
	{
		iframeObj.style.display='none';
		EIS_Hide_Frame();
	}
	if(activeSelectBoxMonth)activeSelectBoxMonth.className='';
	if(activeSelectBoxYear)activeSelectBoxYear.className='';

	if (calFilter)
	{
		calFilter.style.display='none';
	}
	document.body.style.overflow = 'auto';
	document.body.onscroll = null;
	displayFlag = "onlyOne";
}

function writeTopBar()
{
	var topBar = document.createElement('DIV');
	topBar.className = 'topBar';
	topBar.id = 'topBar';

	topBar.style.width = calDivWidth;
	calendarDiv.appendChild(topBar);

	// Left arrow
	var leftDiv = document.createElement('DIV');
	leftDiv.style.marginRight = '1px';
	var img = document.createElement('IMG');
	img.src = pathToImages + 'left.gif';
	img.onmouseover = highlightArrow;
	img.onclick = switchMonth;
	img.onmouseout = highlightArrow;
	topBar.appendChild(img);

	// Right arrow
	var rightDiv = document.createElement('DIV');
	rightDiv.style.marginRight = '1px';
	var img = document.createElement('IMG');
	img.src = pathToImages + 'right.gif';
	img.onclick = switchMonth;
	img.onmouseover = highlightArrow;
	img.onmouseout = highlightArrow;
	img.style.marginLeft = "1px";
	topBar.appendChild(img);

	// Month selector
	var monthDiv = document.createElement('DIV');
	monthDiv.id = 'monthSelect';
	monthDiv.onmouseover = highlightSelect;
	monthDiv.onmouseout = highlightSelect;
	monthDiv.onclick = showMonthDropDown;
	monthDiv.style.width = '64px';
	var span = document.createElement('SPAN');
	span.innerHTML = monthArray[currentMonth];
	span.id = 'calendar_month_txt';
	monthDiv.appendChild(span);

	var img = document.createElement('IMG');
	img.src = pathToImages + 'down.gif';
	img.style.position = 'absolute';
	img.style.right = '0px';
	monthDiv.appendChild(img);
	monthDiv.className = 'selectBox';
	if(Opera)
	{
		img.style.cssText = 'float:right;position:relative';
		img.style.position = 'relative';
		img.style.styleFloat = 'right';
	}
	topBar.appendChild(monthDiv);

	var monthPicker = createMonthDiv();
	if(showWeekNumber == true)
	{
		monthPicker.style.left = '37px';
	}
	else
	{
		monthPicker.style.left = '49px';
	}
	monthPicker.style.top = monthDiv.offsetTop + monthDiv.offsetHeight + 1 + 'px';
	monthPicker.style.width ='64px';
	monthPicker.id = 'monthDropDown';
	calendarDiv.appendChild(monthPicker);

	// Year selector
	var yearDiv = document.createElement('DIV');
	yearDiv.onmouseover = highlightSelect;
	yearDiv.onmouseout = highlightSelect;
	yearDiv.onclick = showYearDropDown;
	yearDiv.style.width = '60px';
	var span = document.createElement('SPAN');
	span.innerHTML = currentYear;
	span.id = 'calendar_year_txt';
	yearDiv.appendChild(span);
	topBar.appendChild(yearDiv);
	var img = document.createElement('IMG');
	img.src 					= pathToImages + 'down.gif';
	img.style.position 			= 'absolute';
	img.style.right 			= '0px';
	yearDiv.appendChild(img);
	yearDiv.className 			= 'selectBox';

	if(Opera)
	{
		yearDiv.style.width 	= '50px';
		img.style.cssText 		= 'float:right';
		img.style.position 		= 'relative';
		img.style.styleFloat 	= 'right';
	}
	var yearPicker = createYearDiv();
	if(showWeekNumber == true)
	{
		yearPicker.style.left 	= '113px';
	}
	else
	{
		yearPicker.style.left 	= '113px';
	}
	yearPicker.style.top 		= monthDiv.offsetTop + monthDiv.offsetHeight + 1 + 'px';
	yearPicker.style.width 		= '60px';
	yearPicker.id 				= 'yearDropDown';
	calendarDiv.appendChild(yearPicker);
	// Close
	var img = document.createElement('IMG');
	img.src 					= pathToImages + 'close.gif';
	img.style.styleFloat 		= 'right';
	img.onmouseover 			= highlightClose;
	img.onmouseout 				= highlightClose;
	img.onclick 				= closeCalendar;
	topBar.appendChild(img);
	if(!document.all)
	{
		img.style.position 		= 'absolute';
		img.style.right 		= '2px';
	}
}

// 現在の表示モードを保持（初期値はonlyOne）
var displayFlag = "onlyOne";
function verticalDisplay()
{
	displayFlag ="vertical";
	if (calendarDiv)
	{
		calendarNextDiv.style.display 			= "block";
		calendarNextDiv.style.width 			= calendarContentDiv.offsetWidth;
		calendarDiv.style.width 				= calendarContentDiv.offsetWidth;
		calendarDiv.style.height 				= calendarContentDiv.offsetHeight + calendarNextDiv.offsetHeight + topBar.offsetHeight;
	}
	calendarDiv.style.boxSizing = "";
	topBar.style.width = calendarContentDiv.offsetWidth;
}
function horizontalDisplay()
{
	onlyOneDisplay();
	var borderWidth = calendarBordr * 2;
	displayFlag ="horizontal";
	if (calendarDiv)
	{
		calendarContentDiv.style.styleFloat 	= "left";
		calendarNextDiv.style.display 			= "block";
		calendarNextDiv.style.styleFloat 		= "left";
		calendarDiv.style.boxSizing 			= "border-box";
		calendarNextDiv.style.width 			= calendarContentDiv.offsetWidth;
		calendarDiv.style.width 				= calendarContentDiv.clientWidth + calendarNextDiv.clientWidth + borderWidth;
		calendarDiv.style.height 				= (calendarContentDiv.offsetHeight > calendarNextDiv.offsetHeight)
													?calendarContentDiv.offsetHeight + topBar.offsetHeight + borderWidth
														:calendarNextDiv.offsetHeight + topBar.offsetHeight + borderWidth;
		topBar.style.width = calendarContentDiv.offsetWidth + calendarNextDiv.offsetWidth;
	}
}

function onlyOneDisplay()
{
	verticalDisplay();
	displayFlag ="onlyOne";
	if (calendarDiv)
	{
		calendarDiv.style.width = calendarContentDiv.offsetWidth;
		calendarDiv.style.height = calendarContentDiv.offsetHeight + topBar.offsetHeight;
	}
	if(calendarNextDiv)
	{
		calendarNextDiv.style.display ="none";
	}
	calendarDiv.style.boxSizing = "";
	topBar.style.width = calendarContentDiv.offsetWidth;
}

function writeCalendarContent()
{
	var calendarContentDivExists = true;
	var calendarNextDivExists = true;
	if(!calendarContentDiv)
	{
		calendarContentDiv = document.createElement('DIV');
		calendarDiv.appendChild(calendarContentDiv);
		calendarContentDivExists = false;
	}
	if(!calendarNextDiv)
	{
		calendarNextDiv  = document.createElement('DIV');
		calendarNextDiv.style.display = "none";
		calendarDiv.appendChild(calendarNextDiv);
		calendarNextDivExists = false;
	}
	// 年月の設定
	currentMonth = currentMonth / 1;
	nextMonth = (currentMonth + 1 ) / 1;
	if (nextMonth > 11)
	{
		nextMonth = 0;
		nextYear = currentYear + 1;
	}
	else
	{
		nextYear = currentYear;
	}

	// ツールバーに年月
	$('calendar_year_txt').innerHTML = currentYear;
	$('calendar_month_txt').innerHTML = monthArray[currentMonth];

	// カレンダーコンテンツの生成
	createCalendarContentsDiv("current", currentYear, currentMonth, calendarContentDiv);
	createCalendarContentsDiv("next", nextYear, nextMonth, calendarNextDiv);

	if(iframeObj)
	{
		if(!calendarContentDivExists || !calendarNextDivExists)setTimeout('resizeIframe()',350);
		else setTimeout('resizeIframe()',10);
	}

	if(!document.all)
	{
		if(calendarContentDiv.offsetHeight)
			$('topBar').style.top = calendarContentDiv.offsetHeight
			+ $('timeBar').offsetHeight + $('topBar').offsetHeight -1 + 'px';
		else
		{
			$('topBar').style.top = '';
			$('topBar').style.bottom = '0px';
		}
	}
	if (displayFlag=="vertical")
	{
		verticalDisplay();
	}
	else if (displayFlag=="horizontal")
	{
		horizontalDisplay();
	}
	else if (displayFlag=="onlyOne")
	{
		horizontalDisplay();
		onlyOneDisplay();
		if (calendarDiv.offsetHeight > 0 && calendarContentDiv.offsetHeight + topBar.offsetHeigh + calendarBordr*2> calendarDiv.offsetHeight)
		{
			calendarDiv.style.height = calendarContentDiv.offsetHeight + topBar.offsetHeigh + calendarBordr*2;
		}
	}

	if(!calFilter)
	{
		createFilter(false);
	}
	else
	{
		createFilter(true);
	}
}

function resizeIframe()
{
	iframeObj.style.width = calendarDiv.offsetWidth -16;
	iframeObj.style.height = calendarDiv.offsetHeight - 16;
}

function pickTodaysDate()
{
	var d = new Date();
	currentMonth = d.getMonth();
	currentYear = d.getFullYear();
	pickDate(false,d.getDate());
}

function pickDate(e,inputDay)
{
	var month = currentMonth / 1 + 1;
	if(month < 10)month = '0' + month;
	var day;

	if(!inputDay && this)day = this.innerHTML;
	else day = inputDay;

	if(day / 1 < 10)day = '0' + day;
	if(returnFormat)
	{
		returnFormat = returnFormat.replace('dd',day);
		returnFormat = returnFormat.replace('mm',month);
		returnFormat = returnFormat.replace('yyyy',currentYear);
		returnFormat = returnFormat.replace('hh',currentHour);
		returnFormat = returnFormat.replace('ii',currentMinute);
		returnFormat = returnFormat.replace('d',day/1);
		returnFormat = returnFormat.replace('m',month/1);

		returnDateTo.value = returnFormat;
		try
		{
			returnDateTo.onchange();
		}
		catch(e){}
	}
	else
	{
		for(var no = 0; no < returnDateToYear.options.length; no++)
		{
			if(returnDateToYear.options[no].value == currentYear)
			{
				returnDateToYear.selectedIndex = no;
				break;
			}
		}
		for(var no = 0; no < returnDateToMonth.options.length; no++)
		{
			if(returnDateToMonth.options[no].value == parseInt(month))
			{
				returnDateToMonth.selectedIndex=no;
				break;
			}
		}
		for(var no = 0; no < returnDateToDay.options.length; no++)
		{
			if(returnDateToDay.options[no].value==parseInt(day))
			{
				returnDateToDay.selectedIndex=no;
				break;
			}
		}
	}
	closeCalendar();
}

function pickNextMonthDate(e,inputDay)
{
	var month = nextMonth / 1 + 1;
	if (nextMonth > 11)
	{
		month = 1;
		nextYear = currentYear + 1;
	}
	if(month < 10)month = '0' + month;
	var day;

	if(!inputDay && this)day = this.innerHTML;
	else day = inputDay;

	if(day / 1 < 10)day = '0' + day;

	if(returnFormat)
	{
		returnFormat = returnFormat.replace('dd',day);
		returnFormat = returnFormat.replace('mm',month);
		returnFormat = returnFormat.replace('yyyy',nextYear);
		returnFormat = returnFormat.replace('hh',currentHour);
		returnFormat = returnFormat.replace('ii',currentMinute);
		returnFormat = returnFormat.replace('d',day/1);
		returnFormat = returnFormat.replace('m',month/1);
		returnDateTo.value = returnFormat;
		try
		{
			returnDateTo.onchange();
		}
		catch(e){}
	}
	else
	{
		for(var no = 0; no < returnDateToYear.options.length; no++)
		{
			if(returnDateToYear.options[no].value == nextYear)
			{
				returnDateToYear.selectedIndex = no;
				break;
			}
		}
		for(var no = 0; no < returnDateToMonth.options.length; no++)
		{
			if(returnDateToMonth.options[no].value == parseInt(month))
			{
				returnDateToMonth.selectedIndex=no;
				break;
			}
		}
		for(var no = 0; no < returnDateToDay.options.length; no++)
		{
			if(returnDateToDay.options[no].value==parseInt(day))
			{
				returnDateToDay.selectedIndex=no;
				break;
			}
		}
	}
	closeCalendar();
}


// This function is from http://www.codeproject.com/csharp/gregorianwknum.asp
// Only changed the month add
function getWeek(year, month, day)
{
	if (! weekStartsOnSunday)
	{
		day = (day / 1);
	}
	else
	{
		day = (day / 1) + 1;
	}
	year = year /1;
    month = month/1 + 1; //use 1-12
    var a = Math.floor( ( 14 - (month) ) / 12 );
    var y = year + 4800 - a;
    var m = (month) + (12 * a) - 3;
    var jd = day + Math.floor( ( ( 153 * m ) + 2 ) / 5 ) +
                 ( 365 * y ) + Math.floor( y / 4 ) - Math.floor( y / 100 ) +
                 Math.floor( y / 400 ) - 32045;      // (gregorian calendar)
    var d4 = ( jd + 31741 - ( jd % 7 ) ) % 146097 % 36524 % 1461;
    var L = Math.floor( d4 / 1460 );
    var d1 = ( ( d4 - L ) % 365 ) + L;
    NumberOfWeek = Math.floor( d1 / 7 ) + 1;
    return NumberOfWeek;
}

var saveTop = 0;
var saveLeft = 0;
var scrollObj = null;
function getTopPos(inputObj)
{
	// カレンダーが存在した場合、Y座標位置を初期化します
	if (calendarDiv !=null && inputObj.tagName != "DIV") calendarDiv.style.top = 0;
	// ボタン位置の計算用変数
	var topCalc = inputObj;
	var returnValue = inputObj.offsetTop;
	while((topCalc = topCalc.offsetParent) != null)
	{
		returnValue += topCalc.offsetTop;
	}
	// ボタンを内包するスクロール要素があるか調べる
	// 存在する場合は、スクロール位置を座標位置から引く
	while((inputObj = inputObj.parentElement) != null)
	{
		if (inputObj.style.overflowY == "scroll")
		{
			returnValue = returnValue - inputObj.scrollTop;
			saveTop = returnValue;
			scrollObj = inputObj;
		}
	}
	return returnValue;
}

function getleftPos(inputObj)
{
	// カレンダーが存在した場合、X座標位置を初期化します
	if (calendarDiv !=null && inputObj.tagName != "DIV") calendarDiv.style.left = 0;
	// ボタン位置の計算用変数
	var leftCalc = inputObj;
	// ボタンの幅分も加える
	var returnValue = inputObj.offsetLeft + leftCalc.offsetWidth;
	while((leftCalc = leftCalc.offsetParent) != null)
	{
		returnValue += leftCalc.offsetLeft;
	}
	saveLeft = returnValue;
	// ボタンを内包するスクロール要素があるか調べる
	// 存在する場合は、スクロール位置を座標位置から引く
	while((inputObj = inputObj.parentElement) != null)
	{
		if (inputObj.style.overflowX == "scroll")
		{
			returnValue = returnValue  - inputObj.scrollLeft;
			saveLeft = returnValue;
			scrollObj = inputObj;
		}
	}
	return returnValue ;
}

function positionCalendar(inputObj)
{
	calendarDiv.style.left = getleftPos(inputObj) + 'px';
	calendarDiv.style.top = getTopPos(inputObj) + 'px';
	if(iframeObj)
	{
		iframeObj.style.left = calendarDiv.style.left;
		iframeObj.style.top =  calendarDiv.style.top;
		iframeObj2.style.left = calendarDiv.style.left;
		iframeObj2.style.top =  calendarDiv.style.top;
	}
}

var calendarHeightSave;
var calendarTopSave;

function createFilter(exist)
{
	if (!exist)
	{
		calFilter = document.createElement('DIV');
		calFilter.style.position 	= "absolute";
		calFilter.style.background 	= "black";
		calFilter.style.zIndex 		= "999";
		calFilter.style.filter 		= 'alpha(opacity=40)';
		document.body.appendChild(calFilter);
		document.body.style.height ="";
	}
	calFilter.style.display 		= "block";
	calFilter.style.width = "100%";
	calFilter.style.height = screen.availHeight + parseInt( document.body.scrollTop );

	// フィルター表示時はスクロールを隠す
	document.body.style.overflow = 'hidden';

	calFilter.style.top =  0;
	calFilter.style.left =  0;
}

function initCalendar()
{
	if(MSIE)
	{
		iframeObj = document.createElement('IFRAME');
		iframeObj.style.filter 			= 'alpha(opacity=0)';
		iframeObj.style.position		= 'absolute';
		iframeObj.border				='0px';
		iframeObj.style.border 			= '0px';
		iframeObj.style.backgroundColor = '#FF0000';

		iframeObj2 = document.createElement('IFRAME');
		iframeObj2.style.position 		= 'absolute';
		iframeObj2.border				='0px';
		iframeObj2.style.border 		= '0px';
		iframeObj2.style.height 		= '1px';
		iframeObj2.style.width 			= '1px';
		// Added fixed for HTTPS
		iframeObj2.src 					= 'blank.html';
		iframeObj.src 					= 'blank.html';
		document.body.appendChild(iframeObj2);
		document.body.appendChild(iframeObj);
	}
	calendarDiv = document.createElement('DIV');
	calendarDiv.id = 'calendarDiv';
	calendarDiv.style.zIndex = 1000;
	slideCalendarSelectBox();
	document.body.appendChild(calendarDiv);
	// focusを移動させる
	document.activeElemwent = calendarDiv;
	writeTopBar();

	if(!currentYear)
	{
		var d = new Date();
		currentMonth = d.getMonth();
		currentYear = d.getFullYear();
	}
	if(!nextYear)
	{
		var nextd = new Date();
		nextMonth = currentMonth + 1;
		if (nextMonth > 11)
		{
			nextYear = currentYear + 1;
		}
		else
		{
			nextYear = currentYear;
		}
	}
	writeCalendarContent();
}

function setTimeProperties()
{
	if(!calendarDisplayTime)
	{
		$('timeBar').style.display='none';
		$('timeBar').style.visibility='hidden';
		$('todaysDateString').style.width = '100%';
	}
	else
	{
		$('timeBar').style.display='block';
		$('timeBar').style.visibility='visible';
		$('hourDropDown').style.top = $('calendar_minute_txt').parentNode.offsetHeight + calendarContentDiv.offsetHeight + $('topBar').offsetHeight + 'px';
		$('minuteDropDown').style.top = $('calendar_minute_txt').parentNode.offsetHeight + calendarContentDiv.offsetHeight + $('topBar').offsetHeight + 'px';
		$('minuteDropDown').style.right = '50px';
		$('hourDropDown').style.right = '50px';
		$('todaysDateString').style.width = '115px';
	}
}

function calendarSortItems(a,b)
{
	return a/1 - b/1;
}

/**
 * カレンダーを表示します。
 * controle.js内のcalendar()関数から呼び出されます。
 * @param inputField
 * @param format
 * @param buttonObj
 * @param displayTime
 * @param timeInput
 * @returns
 */
function displayCalendar(inputField,format,buttonObj,displayTime)
{
	// テキストボックスに値が入力されているとき
	if(inputField.value.length > 6)
	{
		if(!inputField.value.match(/^[0-9]*?$/gi))
		{
			var items = inputField.value.split(/[^0-9]/gi);
			var positionArray = new Object();
			positionArray.m = format.indexOf('mm');
			if(positionArray.m == -1)positionArray.m = format.indexOf('m');
			positionArray.d = format.indexOf('dd');
			if(positionArray.d == -1)positionArray.d = format.indexOf('d');
			positionArray.y = format.indexOf('yyyy');
			positionArray.h = format.indexOf('hh');
			positionArray.i = format.indexOf('ii');
			this.initialHour = '00';
			this.initialMinute = '00';
			var elements = ['y','m','d','h','i'];
			var properties = ['currentYear','currentMonth','inputDay','currentHour','currentMinute'];
			var propertyLength = [4,2,2,2,2];
			for(var i = 0; i < elements.length; i++)
			{
				if(positionArray[elements[i]] >= 0)
				{
					window[properties[i]] = inputField.value.substr(positionArray[elements[i]],propertyLength[i])/1;
				}
			}
			currentMonth--;
		}
		else
		{
			var monthPos = format.indexOf('mm');
			currentMonth = inputField.value.substr(monthPos,2)/1 -1;
			var yearPos = format.indexOf('yyyy');
			currentYear = inputField.value.substr(yearPos,4);
			var dayPos = format.indexOf('dd');
			tmpDay = inputField.value.substr(dayPos,2);

			var hourPos = format.indexOf('hh');
			if(hourPos >= 0)
			{
				tmpHour = inputField.value.substr(hourPos,2);
				currentHour = tmpHour;
			}
			else
			{
				currentHour = '00';
			}
			var minutePos = format.indexOf('ii');
			if(minutePos >= 0)
			{
				tmpMinute = inputField.value.substr(minutePos,2);
				currentMinute = tmpMinute;
			}
			else
			{
				currentMinute = '00';
			}
		}
	}
	else
	{
		var d = new Date();
		currentMonth = d.getMonth();
		currentYear = d.getFullYear();
		currentHour = '08';
		currentMinute = '00';
		inputDay = d.getDate()/1;
	}
	inputYear = currentYear;
	inputMonth = currentMonth;
	if(!calendarDiv)
	{
		initCalendar();
	}
	else
	{
		if(calendarDiv.style.display == 'block')
		{
			closeCalendar();
			return false;
		}
		writeCalendarContent();
	}

	returnFormat = format;
	returnDateTo = inputField;
	positionCalendar(buttonObj);
	calendarDiv.style.visibility = 'visible';
	calendarDiv.style.display = 'block';
	calendarDiv.style.borderWidth = calendarBordr;
	onlyOneDisplay();
	if(iframeObj)
	{
		iframeObj.style.display = 'block';
		iframeObj.style.height = calendarDiv.offsetHeight -16;
		iframeObj.style.width = calendarDiv.offsetWidth -16;
		iframeObj2.style.display = 'block';
		iframeObj2.style.height = calendarDiv.offsetHeight -16;
		iframeObj2.style.width = calendarDiv.offsetWidth -16;
	}
	updateYearDiv();
	updateMonthDiv();
}

// BlueDOG
function dayMouseOver()
{
	if(this.className != 'activeDay')
	this.className = 'dayMouseOver';
}
function dayMouseOut()
{
	if(this.className != 'activeDay')
	this.className = 'dayMouseOut';
}

function createCalendarContentsDiv(targetMonth, Year, Month, targetDiv)
{
	var existingMonthDis = targetDiv.getElementsByTagName('div');
	if(existingMonthDis.length > 0)
	{
		targetDiv.removeChild(existingMonthDis[0]);
	}

	var d = new Date();
	d.setFullYear(Year);
	d.setDate(1);
	d.setMonth(Month);

	var dayStartOfMonth = d.getDay();
	if (! weekStartsOnSunday)
	{
		if(dayStartOfMonthTest == 0)
		{
			dayStartOfMonth = 7;
		}
		dayStartOfMonth--;
	}

	var MonthDis = document.createElement('div');
	MonthDis.id = targetMonth + "MonthDisplay";
	MonthDis.className = "monthDisplay";
	var MonthSpan = document.createElement("span");
	MonthSpan.innerHTML = monthArray[Month];
	MonthDis.style.paddingLeft = "2px";
	MonthDis.style.boxSizing = "border-box";
	MonthDis.style.width = calDivWidth;
	appendImage(MonthDis);
	MonthDis.appendChild(MonthSpan);

	targetDiv.appendChild(MonthDis);

	var existingTable = targetDiv.getElementsByTagName('TABLE');
	if(existingTable.length > 0)
	{
		targetDiv.removeChild(existingTable[0]);
	}
	var calTable = document.createElement('TABLE');

	calTable.id = targetMonth + "Table";
	calTable.style.width = calDivWidth;
	calTable.cellSpacing = '0';
	targetDiv.appendChild(calTable);

	var calTBody = document.createElement('TBODY');
	calTable.appendChild(calTBody);

	var row = calTBody.insertRow(-1);
	row.className = 'calendar_week_row';

	if (showWeekNumber)
	{
		var cell = row.insertCell(-1);
		cell.innerHTML = weekString;
		cell.className = 'calendar_week_column';
		cell.style.backgroundColor = selectBoxRolloverBgColor;
	}
	for(var no = 0; no < dayArray.length; no++)
	{
		var cell = row.insertCell(-1);
		cell.innerHTML = dayArray[no];
//		BlueDOG
		cell.className = 'calendar_week';
		if (no == 0)
		{
			cell.style.color = "red";
		}
		else if (no == 6)
		{
			cell.style.color = "blue";
		}
	}
	var row = calTBody.insertRow(-1);
	if (showWeekNumber)
	{
	   var cell = row.insertCell(-1);
	   cell.className = 'calendar_week_column';
	   cell.style.backgroundColor = selectBoxRolloverBgColor;
	   var week = getWeek(Year,Month,1);
	   cell.innerHTML = week;		// Week
	}
	for(var no = 0; no < dayStartOfMonth; no++)
	{
		var cell = row.insertCell(-1);
		cell.innerHTML = '&nbsp;';
		cell.className = 'calendar_week_blank';
	}
	var colCounter = dayStartOfMonth;
	var daysInMonth = daysInMonthArray[Month];
	if(daysInMonth == 28)
	{
		if(isLeapYear(Year))daysInMonth = 29;
	}

	for(var no = 1; no <= daysInMonth; no++)
	{
		d.setDate(no-1);
		if(colCounter > 0 && colCounter % 7 == 0)
		{
			var row = calTBody.insertRow(-1);
			if (showWeekNumber)
			{
				var cell = row.insertCell(-1);
				cell.className = 'calendar_week_column';
				var week = getWeek(Year,Month,no);
				cell.innerHTML = week;		// Week
				cell.style.backgroundColor = selectBoxRolloverBgColor;
			}
		}
		var cell = row.insertCell(-1);
		cell.innerHTML = no;
		if (colCounter % 7 == 0)
		{
			cell.style.color ='red';
		}
		else if ( colCounter % 7  == 6)
		{
			cell.style.color ='blue';
		}
		if (targetMonth == "next") cell.onclick = pickNextMonthDate;
		else cell.onclick = pickDate;

		cell.onmouseover = dayMouseOver;
		cell.onmouseout = dayMouseOut;
		colCounter++;
		if(Year == inputYear && Month == inputMonth && no == inputDay)
		{
			cell.className='activeDay';
		}
	}
	return MonthDis;
}

function appendImage(MonthDis)
{
	var imgV = document.createElement('IMG');
	imgV.src = pathToImages + 'vertical.png';
	imgV.style.styleFloat = 'right';
	imgV.onclick = verticalDisplay;
	imgV.style.cursor = "pointer";
	imgV.style.verticalAlign = "middle";
	MonthDis.appendChild(imgV);
	if(!document.all)
	{
		imgV.style.position = 'absolute';
		imgV.style.right = '2px';
	}

	var imgH = document.createElement('IMG');
	imgH.src = pathToImages + 'horizontal.png';
	imgH.style.styleFloat = 'right';
	imgH.onclick = horizontalDisplay;
	imgH.style.cursor = "pointer";
	MonthDis.appendChild(imgH);
	if(!document.all)
	{
		imgH.style.position = 'absolute';
		imgH.style.right = '16px';
	}

	var imgO = document.createElement('IMG');
	imgO.src = pathToImages + 'onlyOne.png';
	imgO.style.styleFloat = 'right';
	imgO.onclick = onlyOneDisplay;
	imgO.style.cursor = "pointer";
	MonthDis.appendChild(imgO);
	if(!document.all)
	{
		imgO.style.position = 'absolute';
		imgO.style.right = '30px';
	}
}
