PID=$(ps -ef | grep  pupa.jar | grep -v grep | awk '{ print $2 }')

if [ -z "$PID" ]
then
    echo "pupa.jar程序未启动,正在启动中..。"
else
    echo "$PID pupa.jar程序启动中，正在停止之后重启..."
    kill -9 $PID
fi


nohup java -jar -Xms256M -Xmx256m  pupa.jar > pupa.log 2>&1 &
