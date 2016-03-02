#!/bin/sh
JARFile=target/JFNWAT-1.0.jar
PIDFile=bin/jfnwat.pid
check_if_pid_file_exists () {
if [ ! -f $PIDFile ]
then
echo "PID file not found: $PIDFile"
exit 1
fi
}
check_if_process_is_running () {
if [ ! -f $PIDFile ]
then
return 1
fi
if ps -p $(print_process) > /dev/null
then
return 0
else
return 1
fi
}
print_process (){
echo $(cat $PIDFile)
}
case "$1" in
status)
check_if_pid_file_exists
if check_if_process_is_running
then
echo $(print_process)" is running"
else
echo "Process not running: $(print_process)"
fi
;;
stop)
check_if_pid_file_exists
if ! check_if_process_is_running
then
echo "Process $(print_process) already stopped"
exit 0
fi
kill -TERM $(print_process)
echo "\nWaiting for process to stop"
NOT_KILLED=1
for i in {1..20}; do
if check_if_process_is_running
then
echo "\n."
sleep 1
else
NOT_KILLED=0
fi
done
echo "Process stopped"
;;
start)
if [ -f $PIDFile ] && check_if_process_is_running
then
echo "Process $(print_process) already running"
exit 1
fi
mvn package -DskipTests
java -jar $JARFile &
echo "Process started"
;;
restart)
$0 stop
if [ $? = 1 ]
then
exit 1
fi
$0 start
;;
*)
echo "Usage: $0 {start|stop|restart|status}"
exit 1
esac
exit 0

