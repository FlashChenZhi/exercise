package jp.co.daifuku.emanager.database.entity;

import java.io.Serializable;

/**
 * Designer : Muneendra <BR>
 * 
 * <en> This entity provides Submenu and function Entity information </en>
 */
public class SubmenuFunction
        extends SubMenu
        implements Serializable
{
    /** <jp>機能画面エンティティの配列 &nbsp;&nbsp;</jp><en>Function array &nbsp;&nbsp;</en> */
    private Function[] functionArray = null;

    /**
     * <en>Returns functionArray.</en>
     * 
     * @return functionArray
     */
    public Function[] getFunctionArray()
    {
        return functionArray;
    }

    /**
     * <en>Sets function array. </en>
     * 
     * @param functionArray
     *            array - <en>functionArray to be set.</en>
     */
    public void setFunctionArray(Function[] functionArray)
    {
        this.functionArray = functionArray;
    }

    /**
     * <jp> エンティティにセットされている文字列を返却します。<br></jp>
     * <en> Return the all entity values as one string. <br></en>
     * @return String
     */
    public String toSubmenuFucntionString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("*** SubmenuFunction Entity *********************************\n");
        sb.append(super.toString());

        for (int i = 0; functionArray != null && i < functionArray.length; i++)
        {
            String functionlist = functionArray[i].toString();
            if (i == 0)
            {
                sb.append("    ");
            }
            sb.append(functionlist.replaceAll("\\n", "\n    "));
        }
        sb.append("\n");
        sb.append("************************************************************\n");
        return sb.toString();
    }
}
