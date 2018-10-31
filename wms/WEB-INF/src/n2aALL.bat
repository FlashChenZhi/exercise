rem $Id: n2aALL.bat,v 1.2 2008/10/01 08:15:03 okamura Exp $
rem native2ascii for windows
pushd %~p0
native2ascii -encoding UTF-8 LocaleMapping.properties             ../classes/LocaleMapping.properties
native2ascii -encoding UTF-8 DispResource.properties              ../classes/DispResource.properties
native2ascii -encoding UTF-8 DispResource.properties              ../classes/DispResource_en_US.properties
native2ascii -encoding UTF-8 DispResource_ja_JP.properties        ../classes/DispResource_ja_JP.properties
native2ascii -encoding UTF-8 DispResource_zh_CN.properties        ../classes/DispResource_zh_CN.properties
native2ascii -encoding UTF-8 MessageResource.properties           ../classes/MessageResource.properties
native2ascii -encoding UTF-8 MessageResource.properties           ../classes/MessageResource_en_US.properties
native2ascii -encoding UTF-8 MessageResource_ja_JP.properties     ../classes/MessageResource_ja_JP.properties
native2ascii -encoding UTF-8 MessageResource_zh_CN.properties     ../classes/MessageResource_zh_CN.properties
native2ascii -encoding UTF-8 WMSParam.properties                  ../classes/WMSParam.properties
native2ascii -encoding UTF-8 CommonParam.properties               ../classes/CommonParam.properties
native2ascii -encoding UTF-8 DebugParam.properties                ../classes/DebugParam.properties
native2ascii -encoding UTF-8 ToolParam                            ../classes/ToolParam.properties
native2ascii -encoding UTF-8 eManager.properties                  ../classes/eManager.properties
native2ascii -encoding UTF-8 PatliteLogging.properties            ../classes/PatliteLogging.properties
native2ascii -encoding UTF-8 MessageDef.properties                ../classes/MessageDef.properties
native2ascii -encoding UTF-8 SBPLCmdDef.properties                ../classes/SBPLCmdDef.properties
native2ascii -encoding UTF-8 LabelParam.properties                ../classes/LabelParam.properties
native2ascii -encoding UTF-8 Formats.properties                   ../classes/Formats.properties
native2ascii -encoding UTF-8 Formats.properties                   ../classes/Formats_en_US.properties
native2ascii -encoding UTF-8 Formats_ja_JP.properties             ../classes/Formats_ja_JP.properties
native2ascii -encoding UTF-8 Formats_zh_CN.properties             ../classes/Formats_zh_CN.properties
copy log4j.xml ..\classes\log4j.xml /y
pushd
