// $Id: xmlHttpRequestProgress.js 8041 2012-05-17 09:39:40Z nagao $

function ProgressBar()
{
	// 関数のプロトタイプ宣言
	this.start   = start;
	this.passage = passage;
	this.wait    = wait;
	this.end     = end;
	// 2011/11/15 v5.2.2.6 start
	this.setSuccess = setSuccess;
	var success = false;
	// 2011/11/15 v5.2.2.6 end
	
	// プライベート変数
	var PROGRESSBAR_ID = "___xmlHttpRequestProgressBar";
	var PROGRESSBAR_COUNTER_ID = "___xmlHttpRequestProgressBarCounter";

	var PROGRESSBAR_WIDTH = 200;
	var PROGRESSBAR_MAXCOUNT = 10;
	var PROGRESSBAR_LOADCOUNT = 8;
	var PROGRESSBAR_HEIGHT_OFFSET = 45;
	var PROGRESSBAR_WIDTH_OFFSET = 20;
	var PROGRESSBAR_CREATE_TIMER = 0.2 * 1000;
	var PROGRESSBAR_ENDWAIT_TIMER = 0.8 * 1000;

	var PROGRESS_COLOR    = "#000099";
	var PROGRESS_BGCOLOR  = "#FBFDFF";
	var PROGRESS_MSG      = "#00BFFF";
	var PROGRESS_MSG_LEFT = ( PROGRESSBAR_WIDTH / 2 ) - 5;

	var ID = "id";
	var PROGRESSBAR_TAG = "div";
	var SIZE_SUFFIX = "px";

	var prgTimerId = "";
	var progress = "";
	var count = -1;

	// プログレスバーの描画
	function start()
	{
		if ( progress != "" || prgTimerId != "" )
		{
			removeProgress();
		}

		progress = document.createElement( PROGRESSBAR_TAG );
		progress.setAttribute( ID, PROGRESSBAR_ID );

		var prgStyle = progress.style;

		prgStyle.width = PROGRESSBAR_WIDTH;

		prgStyle.left = document.body.clientWidth - PROGRESSBAR_WIDTH - PROGRESSBAR_WIDTH_OFFSET;
		prgStyle.position = 'absolute';
		prgStyle.filter = "alpha(opacity=90)";

		var top = parseInt( document.body.scrollTop );
		prgStyle.top = ( document.body.clientHeight + top ) - PROGRESSBAR_HEIGHT_OFFSET + SIZE_SUFFIX;

		var left = parseInt( document.body.scrollLeft );
		prgStyle.left  = document.body.clientWidth - PROGRESSBAR_WIDTH - PROGRESSBAR_WIDTH_OFFSET + left;

		document.body.appendChild( progress );

		document.all( PROGRESSBAR_ID ).innerHTML = createProgressBarHTML();
		document.all( PROGRESSBAR_COUNTER_ID ).innerHTML = '';
		createProgressbar( PROGRESSBAR_LOADCOUNT );

		hiddenSelect( document.all( PROGRESSBAR_ID ) );

		window.onscroll = progressScrollFixed;
		window.onresize = progressResizeFixed;

		prgTimerId = setInterval( function(){ createProgressbar( PROGRESSBAR_LOADCOUNT ); }, PROGRESSBAR_CREATE_TIMER );
	}

	// 途中経過
	function passage()
	{
		clearProgressInterval();

		for ( var i = count; i < PROGRESSBAR_LOADCOUNT; i++ )
		{
			createProgressbar( PROGRESSBAR_LOADCOUNT );
		}
	}

	// 待機
	function wait()
	{
		clearProgressInterval();

		for ( var i = count; i < PROGRESSBAR_MAXCOUNT; i++ )
		{
			createProgressbar( PROGRESSBAR_MAXCOUNT );
		}
	}

	// プログレスバーの終了
	function end()
	{
		clearProgressInterval();

		for ( var  i = count; i < PROGRESSBAR_MAXCOUNT; i++ )
		{
			createProgressbar( PROGRESSBAR_MAXCOUNT );
		}
		// プログレスバー削除
		removeProgress();
	}
	// 2011/11/15 v5.2.2.6 start
	function setSuccess()
	{
		success = true;
	}
	// 2011/11/15 v5.2.2.6 end

	// バーの進捗表示
	function createProgressbar( maxCount )
	{
		if ( progress != "" )
		{
			if ( count < maxCount )
			{
				var distance = ( ( count + 1 ) * ( PROGRESSBAR_WIDTH / PROGRESSBAR_MAXCOUNT ) );

				if ( count >= PROGRESSBAR_MAXCOUNT )
				{
					distance = PROGRESSBAR_WIDTH;
				}

				var html  = "<div style='height:100%; width:" + distance + "px;background-color:" + PROGRESS_COLOR + "'align='center'>";
					// html += "<span style='position:absolute; top:0px; left:" + PROGRESS_MSG_LEFT + "px; color:"+ PROGRESS_MSG + ";'>";
					// html += ( ( count + 1 ) * 10 ) + "%";
					// html += "</span>";
					html += "</div>";

				document.getElementById( PROGRESSBAR_COUNTER_ID ).innerHTML = html;
				count++;
			}
		}
	}

	// プログレスバーのインターバル解除
	function clearProgressInterval()
	{
		try
		{
			if ( prgTimerId != "" )
			{
				clearInterval( prgTimerId );
			}
		}
		catch(e)
		{
		}
		prgTimerId = "";
	}

	// プログレスバーの遅延削除処理
	function removeProgressTimer()
	{
		try
		{
			setTimeout( function(){ removeProgress(); }, PROGRESSBAR_ENDWAIT_TIMER );
		}
		catch(e)
		{
			removeProgress();
		}
	}

	// プログレスバーの削除処理
	function removeProgress()
	{
		count = -1;

		// Selectの非表示化
		// 2011/11/15 v5.2.2.6 start
		visibleSelect( progress, success );
		success = false;
		// 2011/11/15 v5.2.2.6 end
		
		if ( progress != "" )
		{
			document.body.removeChild( progress );
			progress = "";
			
			// v5.2.2.7 2011/12/21 start
			window.onscroll = null;
			window.onresize = null;
			// v5.2.2.7 2011/12/21 end

		}
	}

	// プログレスバーのフローティング化
	function progressScrollFixed()
	{
		if ( progress != "" )
		{
			if( document.body.scrollTop >= 0 )
			{
				var top = parseInt( document.body.scrollTop );
				document.getElementById( PROGRESSBAR_ID ).style.top = document.body.clientHeight - PROGRESSBAR_HEIGHT_OFFSET + top;
			}
			if( document.body.scrollLeft >= 0 )
			{
				var left = parseInt( document.body.scrollLeft );
				document.getElementById( PROGRESSBAR_ID ).style.left = document.body.clientWidth - PROGRESSBAR_WIDTH - PROGRESSBAR_WIDTH_OFFSET + left;
			}
		}
	}

	// プログレスバーのフローティング処理
	function progressResizeFixed()
	{
		if ( progress != "" )
		{
			document.getElementById( PROGRESSBAR_ID ).style.top  = document.body.clientHeight - PROGRESSBAR_HEIGHT_OFFSET;
			document.getElementById( PROGRESSBAR_ID ).style.left = document.body.clientWidth - PROGRESSBAR_WIDTH - PROGRESSBAR_WIDTH_OFFSET;
		}
	}

	// 進捗用オブジェクトの構築
	function createProgressBarHTML()
	{
		var html = "<div id='" + PROGRESSBAR_COUNTER_ID + "' style =\"height:20px;border:2px inset; background-color:" + PROGRESS_BGCOLOR + ";\"></div>";
		return html;
	}

	return this;
}
