// $Id: common.js 7996 2011-07-06 00:52:24Z kitamaki $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * JavaScript for Main Menu
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  $Author: kitamaki $
 */

/** selected menu id (number) */
var menu_num = "";

/** context path */
var context_Path;

/** munber of menu */
var menu_count = 0;

/** Type B でのメニュー文字列の表示タイプ
 * normal    : 通常表示
 * tooltip   : マウスオーバーでツールチップ表示
 * stretch   : マウスオーバーで伸縮表示
 */
var labelDispType = "normal";


/**
 *  コンテキストパスを保持します。 
 * @param str 保持するコンテキストパス 
 */
function setContextPath(str)
{
	context_Path = str;
}


/**
 *  表示するメニューアイコンの数を保持します。 
 * @param val 保持する数 
 */
function setMenuCount(val)
{
	menu_count = val;
}


/**
 * メニューポップアップ表示クラス
 */
var MenuPopup =
{
	/** ポップアップオブジェクト
	popupObj : null,

	/**
	 * ポップアップウィドウを表示します。
	 * @param str 表示する文字列
	 * @param obj 表示元のオブジェクト
	 */
	create : function(str, obj)
	{
		if( str == null || str.length==0 )
		{
			return;
		}

		// create a popup object
		this.popupObj = window.createPopup();
		this.popupObj.document.body.style.border = "solid black 1px";
		this.popupObj.document.body.style.backgroundColor = "#f0f0c0";

		// make a popup tag
		this.popupObj.document.body.innerHTML = "<div id='outerDIV' style='padding:5px,5px,5px,5px;'>"
			+"<span style='color:black; font-size:16px; font-family : monospace; word-break:keep-all; white-space: nowrap'>"
			+  str 	+ "</span></div>";

		// get position from base object
		var xPos = obj.clientWidth;
		var yPos = obj.clientHeight;

		// show popup for get real size
		this.popupObj.show(xPos, yPos, 1, 1, obj);

		// get real size of popup object
		var width  = this.popupObj.document.getElementById("outerDIV").offsetWidth;
		var heigth = this.popupObj.document.getElementById("outerDIV").offsetHeight;

		// update real size.
		this.popupObj.show(xPos, yPos, width, heigth, obj);

	},

	/**
	 * ポップアップを閉じます
	 */
	close : function()
	{
		if(this.popupObj != null)
		{
			this.popupObj.hide();
			this.popupObj = null;
		}
	}
};

/**
 * メニュー画像変更関数 
 * @param event  発生したイベント 
 * @param id     対象の画像のID 
 * @param menuno メニューのナンバー 
 */
function change_Image(event, id, menuno)
{
	switch(event)
	{
		case 'over':
		{
			if (menuno == menu_num)
			{
				break;
			}
			else
			{
				document.images[id].src = context_Path + "/img/project/menu/typea/icon_" + menuno + "_1.gif";
				document.getElementById("menut_" + menuno).style.color = "#70E6FF";

				if( labelDispType == "tooltip" )
				{
					MenuPopup.create(document.getElementById("menut_" + menuno).innerText, document.images[id]);
				}
				else if ( labelDispType == "stretch" )
				{
					document.getElementById("menut_" + menuno).style.display = "block";
				}
			}
			break;
		}
		case 'down':
		{
			if (menuno == menu_num)
			{
				break;
			}
			else
			{
				document.images[id].src = context_Path + "/img/project/menu/typea/icon_" + menuno + "_2.gif";
				document.getElementById("menut_" + menuno).style.color = "#FC8800";

			}
			break;
		}
		case 'out':
		{
			if (menuno == menu_num)
			{
				break;
			}
			else
			{
				document.images[id].src = context_Path + "/img/project/menu/typea/icon_" + menuno + "_0.gif";
				document.getElementById("menut_" + menuno).style.color = "#FFFFFF";
				if( labelDispType == "tooltip" )
				{
					MenuPopup.close();
				}
				else if ( labelDispType == "stretch" )
				{
					document.getElementById("menut_" + menuno).style.display = "none";
				}

			}
			break;
		}
		case 'up':
		{
			if (menuno == menu_num)
			{
				break;
			}
			else
			{
				document.images[id].src = context_Path + "/img/project/menu/typea/icon_" + menuno + "_0.gif";
				document.getElementById("menut_" + menuno).style.color = "#FFFFFF";
				if ( labelDispType == "stretch" )
				{
					document.getElementById("menut_" + menuno).style.display = "none";
				}

			}
			break;
		}
		case 'click':
		{
			var menu_c = new setCookie("u_menu");
			var temp_menu_id;
			for (var i = 0 ; i < document.images.length; i++)
			{
				temp_menu_id = document.images[i].id;
				if (temp_menu_id.indexOf("menu_") == 0)
				{
					document.images[temp_menu_id].src = context_Path + "/img/project/menu/typea/icon_" + temp_menu_id.substring(5) + "_0.gif";
				}
				if (document.getElementById("menut_" + temp_menu_id.substring(5)))
				{
					document.getElementById("menut_" + temp_menu_id.substring(5)).style.color = "#FFFFFF";
					if(labelDispType == "stretch" || labelDispType == "tooltip")
					{
						document.getElementById("menut_" + temp_menu_id.substring(5)).style.display = "none";
					}
				}
			}
			document.images[id].src = context_Path + "/img/project/menu/typea/icon_" + menuno + "_2.gif";
			document.getElementById("menut_" + menuno).style.color = "#FC8800";
			if( labelDispType == "tooltip" )
			{
				MenuPopup.close();
				document.getElementById("menut_" + menuno).style.display = "block";
			}
			else if ( labelDispType == "stretch" )
			{
				document.getElementById("menut_" + menuno).style.display = "block";
			}
			menu_num = menuno;
			menu_c.menu_s = menu_num;
			menu_c.store();
			break;
		}
	}
}


/**
 * メニュー画像変更関数 
 * @param event  発生したイベント 
 * @param id     対象の画像のID 
 * @param menuno メニューのナンバー 
 * @param lang   言語（日本語:ja、英語:en など）
 */
function change_ImageB(event, id, menuno, lang)
{
	switch(event)
	{
		case 'over':
		{
			if (menuno != menu_num)
			{
				document.images[id].src = context_Path + "/img/project/menu/typeb/icon2_" + menuno + "_1_" + lang + ".gif";
			}
			break;
		}
		case 'down':
		{
			if (menuno != menu_num)
			{
				document.images[id].src = context_Path + "/img/project/menu/typeb/icon2_" + menuno + "_2_" + lang + ".gif";
			}
			break;
		}
		case 'out':
		{
			if (menuno != menu_num)
			{
				document.images[id].src = context_Path + "/img/project/menu/typeb/icon2_" + menuno + "_0_" + lang + ".gif";
			}
			break;
		}
		case 'up':
		{
			if (menuno == menu_num)
			{
				break;
			}
			else
			{
				document.images[id].src = context_Path + "/img/project/menu/typeb/icon2_" + menuno + "_0_" + lang + ".gif";
			}
			break;
		}
		case 'click':
		{
			var menu_c = new setCookie("u_menu");
			var temp_menu_id;

			for (var i = 0 ; i < document.images.length; i++)
			{
				temp_menu_id = document.images[i].id;
				if (temp_menu_id.indexOf("menu_") == 0)
				{
					document.images[temp_menu_id].src = context_Path + "/img/project/menu/typeb/icon2_" + temp_menu_id.substring(5) + "_0_" + lang + ".gif";
				}
			}
			document.images[id].src = context_Path + "/img/project/menu/typeb/icon2_" + menuno + "_2_" + lang + ".gif";
			menu_num = menuno;
			menu_c.menu_s2 = menu_num;
			menu_c.store();
			break;
		}
	}
}
