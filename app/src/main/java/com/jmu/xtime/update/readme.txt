/*
定时发短信
存储形式HashMap<String,String>
键     ->             值
taskId               类型 String 在数据库中的ID
threadId             类型 String 线程ID
title                类型 String 任务标题
description          类型 String 描述
tel                  类型 String 手机号
content              类型 String 内容
time                 类型 String 发送时间
taskStatus           类型 String on/off



分析短信
键   ->              值
taskId             类型 String 在数据库中的ID
threadId           类型 String 线程ID
title              类型 String 任务标题
description        类型 String 描述
keyWordYes         类型 String 肯定关键词
keyWordNot         类型 String 否定关键词
status             类型 String -> on/off
time               类型 String 发送时间
taskStatus         类型 String on/off

定时GPS
taskId               类型 String 在数据库中的ID
threadId             类型 String 线程ID
title                类型 String 任务标题
description          类型 String 描述
tel                  类型 String 手机号
sendTimeInterval     类型 String 发送间隔
sendTimes            类型 String 发送次数
time                 类型 String 发送时间
taskStatus           类型 String on/off

定时摄像
taskId               类型 String 在数据库中的ID
threadId             类型 String 线程ID
title                类型 String 任务标题
description          类型 String 描述
time                 类型 String 发送时间
taskStatus           类型 String on/off
*/