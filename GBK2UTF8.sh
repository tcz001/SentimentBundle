find ./ -type f -name "*.txt"|while read line;do 
echo $line 
iconv -f GBK -t UTF-8 $line > ${line}.utf8 
mv $line ${line}.gbk 
mv ${line}.utf8 $line 
done 
