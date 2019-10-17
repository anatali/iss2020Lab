%====================================================================================
% smartrobot description   
%====================================================================================
context(ctxsmartrobot, "localhost",  "TCP", "8020").
 qactor( smartrobot, ctxsmartrobot, "it.unibo.smartrobot.Smartrobot").
  qactor( basicrobot, ctxsmartrobot, "it.unibo.basicrobot.Basicrobot").
  qactor( onestpcaller, ctxsmartrobot, "it.unibo.onestpcaller.Onestpcaller").
