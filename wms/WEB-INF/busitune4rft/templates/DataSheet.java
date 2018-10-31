package {$Package};

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.util.Locale;
import jp.co.sharedsys.basis.helper.DataSheetFactory;

{$Imports}

/**
 *
 * @version
 * @author {$Author}
 */
public class {$ClassName}
        extends {$SuperClassName}
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** DATASHEET_NAME */
    private static final String DATASHEET_NAME = "{$DataSheetName}";

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
     * Default constructor
     *
     * @param dsf DataSheet factory
     * @param locale Locale
     */
    public {$ClassName}(DataSheetFactory dsf, Locale locale)
    {
        super(DATASHEET_NAME, dsf, locale);
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
