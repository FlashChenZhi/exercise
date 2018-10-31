package jp.co.daifuku.wms.exercise.util;

import org.apache.commons.lang.StringUtils;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 14-1-20
 * Time: 下午1:11
 * To change this template use File | Settings | File Templates.
 */
public class StringHelper
{
        public static int length(String str)
        {
                return str.replaceAll("[^\\x00-\\xff]", "***").length();
        }

        public static String subString(int startIndex, int length, String str)
        {
                StringBuffer sb = new StringBuffer();

                for (int i = startIndex, j = startIndex; i < startIndex + length; j++)
                {
                        String s = str.substring(j, j + 1);

                        i += length(s);
                        if (i > startIndex + length)
                        {
                                break;
                        }

                        sb.append(s);
                }

                return sb.toString();
        }

        public static String decode(String token)
        {
                return StringUtils.isBlank(token) ? StringUtils.EMPTY : token;
//                if (StringUtils.isBlank(token))
//                {
//                        return StringUtils.EMPTY;
//                }

//                char charToTrim = '"';
//                int lastIndex = token.lastIndexOf(String.valueOf(charToTrim));
//                if (lastIndex == token.length() - 1)
//                {
//                        token = token.substring(0, lastIndex);
//                }
//
//                if (token.startsWith(String.valueOf(charToTrim)))
//                {
//                        token = token.substring(1, token.length() - 1);
//                }
//                return token.replaceAll("\"\"", "\"");
        }

        public static String encode(String token)
        {
                return token;
//                return String.format("\"%s\"", token.replaceAll("\"", "\"\""));
        }
}
