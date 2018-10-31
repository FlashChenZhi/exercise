package {$Package};

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
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
    /** SCREEN_NAME */
    private static final String SCREEN_NAME = "{$ScreenName}";

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
     */
    public {$ClassName}()
    {
        super();
    }

{$Constructors}

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
{$PublicMethods}

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------
    /**
     * returns screen name for business class.
     *
     * @return screen name of HaiSurf.
     */
    public String getScreenName()
    {
        return SCREEN_NAME;
    }

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
     *
     * @return version
     */
    public static String getVersion()
    {
        return "";
    }

{$UtilityMethods}

}
//end of class
