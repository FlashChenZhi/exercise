//$Id: CSVTokenizer.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.common.text ;

import java.util.Enumeration;
import java.util.NoSuchElementException;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


/**
 * CSVTalknizer class comments.<br>
 * 
 * CSV形式をハンドリングするトークナイザークラスです。
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2004/12/27</td><td nowrap>ss</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */

public class CSVTokenizer
		implements Enumeration
{
	//------------------------------------------------------------
	// class variables (prefix '$')
	//------------------------------------------------------------
	//	private String	$classVar ;

	//------------------------------------------------------------
	// fields (upper case only)
	//------------------------------------------------------------
	/** デフォルトの区切り文字 */
	public static final char DELIMITER = ',' ;

	/** デフォルトのクオーテーションマーク */
	public static final char QUOTATION_MARK = '"' ;

	//------------------------------------------------------------
	// properties (prefix 'p_')
	//------------------------------------------------------------
	//	private String	p_Name ;

	//------------------------------------------------------------
	// instance variables (prefix '_')
	//------------------------------------------------------------
	/** 対象となる文字列 */
	private String _sourceString ;

	/** 次の読み出し位置 */
	private int _currentPosition ;

	private int _endPosition ;

	private char _delimiter = DELIMITER ;

	private char _quotation = QUOTATION_MARK ;

	//------------------------------------------------------------
	// constructors
	//------------------------------------------------------------
	/**
	 * CSVの文字列を分割するためのインスタンスを生成します。<br>
	 * 区切り文字(,)やクオーテーション(")はデフォルトのものを使用します。
	 *
	 * @param line CSV形式の文字列  改行コードを含まない。
	 */
	public CSVTokenizer(String line)
	{
		_sourceString = line ;
		_currentPosition = 0 ;
		_endPosition = line.length() ;
	}

	/**
	 * 区切り文字とクオーテーションを指定してインスタンスを生成します。
	 * 
	 * @param line  CSV形式の文字列  改行コードを含まない。
	 * @param delimiter デリミタ (項目の区切り文字)
	 * @param quot クオーテーションマーク (文字列の囲み文字)
	 */
	public CSVTokenizer(String line, char delimiter, char quot)
	{
		this(line) ;

		_delimiter = delimiter ;
		_quotation = quot ;
	}

	//------------------------------------------------------------
	// public methods
	// use {@inheritDoc} in the comment, If the method is overridden.
	//------------------------------------------------------------
	/**
	 * 次のカンマがある位置を返す。
	 *
	 * @param startidx 検索を開始する位置
	 * @return int 次デリミタがある位置。もうデリミタが見つからない場合は、
	 * 	文字列の長さの値が返ります。
	 */
	private int nextDelimiter(int startidx)
	{
		int idx = startidx ;
		boolean inQuot = false ;
		while (idx < _endPosition)
		{
			char tchar = _sourceString.charAt(idx) ;
			if (!inQuot && (tchar == _delimiter))
			{
				break ;
			}
			else if (_quotation == tchar)
			{
				inQuot = !inQuot ;
			}
			idx++ ;
		}
		return idx ;
	}

	/**
	 * 文字列に含まれている項目の数を返します。
	 *
	 * @return int 項目の数
	 */
	public int countTokens()
	{
		int i = 0 ;
		int ret = 1 ;
		while ((i = nextDelimiter(i)) < _endPosition)
		{
			i++ ;
			ret++ ;
		}
		return ret ;
	}

	/**
	 * 次の項目の文字列を返す。
	 *
	 * @return 次の項目
	 * @exception NoSuchElementException 項目が残っていないとき
	 */
	public String nextToken()
	{
		if (_currentPosition > _endPosition)
		{
			throw new NoSuchElementException(toString() + "#nextToken") ;
		}

		int st = _currentPosition ;
		_currentPosition = nextDelimiter(_currentPosition) ;

		StringBuffer strb = new StringBuffer() ;
		while (st < _currentPosition)
		{
			char ch = _sourceString.charAt(st++) ;
			if (ch == _quotation)
			{
				// "が単独で現れたときは何もしない
				if ((st < _currentPosition) && (_sourceString.charAt(st) == _quotation))
				{
					strb.append(ch) ;
					st++ ;
				}
			}
			else
			{
				strb.append(ch) ;
			}
		}
		_currentPosition++ ;
		return new String(strb) ;
	}

	/**
	 * <code>nextToken</code>メソッドと同じで、
	 * 次の項目の文字列を返す。<br>
	 * ただし返値は、String型ではなく、Object型である。<br>
	 * java.util.Enumerationを実装しているため、このメソッドがある。
	 *
	 * @return 次の項目
	 * @exception NoSuchElementException 項目が残っていないとき
	 */
	public Object nextElement()
	{
		return nextToken() ;
	}

	/**
	 * まだ項目が残っているかどうか調べる。
	 *
	 * @return まだ項目がのこっているならtrue
	 */
	public boolean hasMoreTokens()
	{
		return (nextDelimiter(_currentPosition) <= _endPosition) ;
	}

	/**
	 * <code>hasMoreTokens</code>メソッドと同じで、
	 * まだ項目が残っているかどうか調べる。<br>
	 * java.util.Enumerationを実装しているため、このメソッドがある。
	 *
	 * @return まだ項目がのこっているならtrue
	 */
	public boolean hasMoreElements()
	{
		return hasMoreTokens() ;
	}

	//------------------------------------------------------------
	// accessor methods
	// use {@inheritDoc} in the comment, If the method is overridden.
	//------------------------------------------------------------


	//------------------------------------------------------------
	// package methods
	// use {@inheritDoc} in the comment, If the method is overridden.
	//------------------------------------------------------------


	//------------------------------------------------------------
	// protected methods
	// use {@inheritDoc} in the comment, If the method is overridden.
	//------------------------------------------------------------


	//------------------------------------------------------------
	// private methods
	//------------------------------------------------------------


	//------------------------------------------------------------
	// utility methods
	//------------------------------------------------------------
	/**
	 * このクラスのリビジョンを返します。
	 * @return リビジョン文字列。
	 */
	public static String getVersion()
	{
		return "$Id: CSVTokenizer.java 87 2008-10-04 03:07:38Z admin $" ;
	}
}
