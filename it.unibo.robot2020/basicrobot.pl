%====================================================================================
% basicrobot description   
%====================================================================================
context(ctxbasicrobot, "localhost",  "TCP", "8020" ).
 qactor( basicrobot, ctxbasicrobot, "it.unibo.basicrobot.Basicrobot").
  qactor( sonarhandler, ctxbasicrobot, "it.unibo.sonarhandler.Sonarhandler").
