package {$Package};

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.foundation.common.Params;

{$Imports}

/**
 *
 * @version
 * @author {$Author}.
 */
public class {$ClassName}
        extends {$SuperClassName}
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
{$Fields}

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
{$ClassVariables}

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
{$InstanceVariables}

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Constructor to create SCH object
     * @param conn Database Connection
     * @param parent Caller Class
     * @param locale Browser Locale
     */
    public {$ClassName}(Connection conn, Class parent, Locale locale)
    {
        super(conn, parent, locale);
    }

{$Constructors}

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
{$PublicMethods}

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------
{$AccessorMethods}

    //------------------------------------------------------------
    // package methods
    //------------------------------------------------------------
{$PackageMethods}

    //------------------------------------------------------------
    // protected methods
    //------------------------------------------------------------
{$ProtectedMethods}

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
{$PrivateMethods}

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * Returns current repository info for this class
     * @return
     */
    public static String getVersion()
    {
        return "";
    }

{$UtilityMethods}

}
//end of class
