case "$1" in
   "java") java $2 
   ;;
   "python") python $2 
   ;;
   "c") gcc $2 -std=c99
   ;;
   "c++") g++ $2
   ;;
esac
