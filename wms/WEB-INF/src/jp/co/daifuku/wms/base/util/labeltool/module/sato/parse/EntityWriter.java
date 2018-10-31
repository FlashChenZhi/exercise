// $Id: EntityWriter.java 2583 2009-01-08 00:20:09Z kumano $
package jp.co.daifuku.wms.base.util.labeltool.module.sato.parse ;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.BufferedWriter ;
import java.io.File ;
import java.io.FileNotFoundException ;
import java.io.FileOutputStream ;
import java.io.IOException ;
import java.io.OutputStreamWriter ;
import java.io.UnsupportedEncodingException ;

import jp.co.daifuku.wms.base.util.labeltool.LabelParam ;
import jp.co.daifuku.wms.base.util.labeltool.base.LabelConstants ;
import jp.co.daifuku.wms.base.util.labeltool.base.exception.DaiException ;
import jp.co.daifuku.wms.base.util.labeltool.base.util.LabelInfoUtil ;
import jp.co.daifuku.wms.label.xmlbeans.DAILabelDocument ;
import jp.co.daifuku.wms.label.xmlbeans.Variable ;

import org.apache.xmlbeans.XmlException ;


/**
 * Entity自動生成を行うクラスです。
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2008/07/31</td><td nowrap>chenjun</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 2583 $, $Date: 2009-01-08 09:20:09 +0900 (木, 08 1 2009) $
 * @author  chenjun
 * @author  Last commit: $Author: kumano $
 */


public class EntityWriter
{
	/** <code>$INDENT_STR</code> */
	private static String $INDENT_STR = "    " ;

	/** <code>生成したクラス定義内容</code> */
	private StringBuffer context = new StringBuffer() ;

	/**
	 * レイアウトXML定義ファイルからエンティティクラスファイルを生成します。
	 * 
	 * @param xmlFileName XML定義ファイル名
	 * @param entityName エンティティクラス名 
	 * @throws DaiException 異常が発生した場合
	 */
	public void generateEntityFile(String xmlFileName, String entityName)
			throws DaiException
	{
		File xmlFile = new File(xmlFileName) ;
		try
		{
			DAILabelDocument doc = DAILabelDocument.Factory.parse(xmlFile) ;
			Variable[] d = doc.getDAILabel().getVariableArray() ;
			File entityFile = new File(LabelInfoUtil.getLocalEntityPath()
					+ entityName
					+ "Entity.java") ;
			entityFile.delete() ;

			if (d != null && d.length > 0)
			{
				generateClass(entityName, d) ;
			}
		}
		catch (XmlException e)
		{
			throw new DaiException("ERR0029", e) ;
		}
		catch (IOException e)
		{
			throw new DaiException("ERR0029", e) ;
		}
	}

	/**
	 * エンティティクラスを生成します。
	 * 
	 * @param className クラス名
	 * @param v 変数名配列
	 * @throws DaiException 異常が発生した場合
	 */
	private void generateClass(String className, Variable[] v)
			throws DaiException
	{
		writePackageInfo() ;
		writeImportInfo() ;
		writeClassBody(className, v) ;
		saveToFile(className) ;
	}

	/**
	 * パッケージの宣言を記入します。
	 */
	private void writePackageInfo()
	{
		context.append("package " + LabelParam.ENEITY_PACKAGE_PASS + ";" + LabelConstants.CR_STR) ;
		addNewLine() ;
	}

	/**
	 * import宣言を記入します。
	 */
	private void writeImportInfo()
	{
		context.append("import java.util.HashMap;" + LabelConstants.CR_STR) ;
		context.append("import java.util.Map;" + LabelConstants.CR_STR) ;
		addNewLine() ;
	}

	/**
	 * クラス宣言を記入します。
	 * @param className クラス名
	 * @param v 変数名配列
	 */
	private void writeClassBody(String className, Variable[] v)
	{
		context.append("/**" + LabelConstants.CR_STR) ;
		context.append("* XML定義より印字項目を取得、setter、getterを所持しているクラスです。" + LabelConstants.CR_STR) ;
		context.append("*/" + LabelConstants.CR_STR) ;

		context.append("public class " + className + "Entity" + LabelConstants.CR_STR) ;
		context.append("{" + LabelConstants.CR_STR) ;
		writeMapDeclaration() ;
		for (int i = 0; i < v.length; i++)
		{
			writeSetterMethod(v[i].getFieldId()) ;
		}
		writeGetterMethod() ;
		context.append("}" + LabelConstants.CR_STR) ;
	}

	/**
	 * Map宣言を記入します。
	 */
	private void writeMapDeclaration()
	{
		context.append($INDENT_STR + "/**" + LabelConstants.CR_STR) ;
		context.append($INDENT_STR + " * Map" + LabelConstants.CR_STR) ;
		context.append($INDENT_STR + " */" + LabelConstants.CR_STR) ;

		context.append($INDENT_STR
				+ "HashMap<String, String> map = new HashMap<String, String>();"
				+ LabelConstants.CR_STR) ;
		addNewLine() ;
	}

	/**
	 * Setterメソッド宣言を記入します。
	 * @param fieldId 項目名
	 */
	private void writeSetterMethod(String fieldId)
	{
		//String id = fieldId.toLowerCase();
		String id = fieldId ;

		context.append($INDENT_STR + "/**" + LabelConstants.CR_STR) ;
		context.append($INDENT_STR + " * " + id + "をMapにセットします。" + LabelConstants.CR_STR) ;
		context.append($INDENT_STR + " * @param " + id + LabelConstants.CR_STR) ;
		context.append($INDENT_STR + " */" + LabelConstants.CR_STR) ;

		//        context.append($INDENT_STR
		//                + "public void set"
		//                + id.substring(0, 1).toUpperCase()
		//                + id.substring(1)
		//                + "(String "
		//                + id
		//                + ")"
		//                + LabelConstants.CR_STR);
		context.append($INDENT_STR
				+ "public void set"
				+ id
				+ "(String "
				+ id
				+ ")"
				+ LabelConstants.CR_STR) ;

		context.append($INDENT_STR + "{" + LabelConstants.CR_STR) ;
		context.append($INDENT_STR
				+ $INDENT_STR
				+ "map.put(\""
				+ fieldId
				+ "\", "
				+ id
				+ ");"
				+ LabelConstants.CR_STR) ;
		context.append($INDENT_STR + "}" + LabelConstants.CR_STR) ;
		addNewLine() ;
	}

	/**
	 * Getterメソッド宣言を記入します。
	 */
	private void writeGetterMethod()
	{
		context.append($INDENT_STR + "/**" + LabelConstants.CR_STR) ;
		context.append($INDENT_STR + " * Mapデータを取得します。" + LabelConstants.CR_STR) ;
		context.append($INDENT_STR + " * @return" + LabelConstants.CR_STR) ;
		context.append($INDENT_STR + " */" + LabelConstants.CR_STR) ;

		context.append($INDENT_STR + "public Map getMap()" + LabelConstants.CR_STR) ;
		context.append($INDENT_STR + "{" + LabelConstants.CR_STR) ;
		context.append($INDENT_STR + $INDENT_STR + "return map;" + LabelConstants.CR_STR) ;
		context.append($INDENT_STR + "}" + LabelConstants.CR_STR) ;
		addNewLine() ;
	}

	/**
	 * 新しい行を追加します。
	 */
	private void addNewLine()
	{
		context.append(LabelConstants.NEW_LINE_STR) ;
	}

	/**
	 * Javaソースファイルを出力します。
	 * @param className クラスファイル名
	 * @throws DaiException 異常が発生した場合
	 */
	private void saveToFile(String className)
			throws DaiException
	{
		OutputStreamWriter objStreamWriter = null ;
		try
		{

			File entityFile = new File(LabelInfoUtil.getLocalEntityPath()
					+ className
					+ "Entity.java") ;
			if (!entityFile.getParentFile().exists())
			{
				entityFile.getParentFile().mkdirs() ;
			}
			if (!entityFile.exists())
			{
				entityFile.createNewFile() ;
			}
			objStreamWriter = new OutputStreamWriter(new FileOutputStream(
					LabelInfoUtil.getLocalEntityPath() + className + "Entity.java", false), "UTF-8") ;
			BufferedWriter objWriter = new BufferedWriter(objStreamWriter) ;
			objWriter.write(context.toString()) ;
			objWriter.flush() ;
			objWriter.close() ;
			objStreamWriter.close() ;
		}
		catch (UnsupportedEncodingException e)
		{
			throw new DaiException("ERR0029", e) ;
		}
		catch (FileNotFoundException e)
		{
			throw new DaiException("ERR0029", e) ;
		}
		catch (IOException e)
		{
			throw new DaiException("ERR0029", e) ;
		}
	}
}
