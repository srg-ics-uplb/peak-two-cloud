set terminal pngcairo enhanced font "arial,6.5" fontscale 1.0 size 1024, 740
set output 'npb.class.a.png'
set yrange [0:*]
GAPSIZE=4
set style histogram cluster gap 4
STARTCOL=2                 #Start plotting data in this column (2 for your example)
ENDCOL=6                   #Last column of data to plot (10 for your example)
NCOL=ENDCOL-STARTCOL+1     #Number of columns we're plotting 
BOXWIDTH=1./(GAPSIZE+NCOL) #Width of each box.
plot for [COL=STARTCOL:ENDCOL] 'npb.class.a.csv' u COL:xtic(1) w histogram title columnheader(COL), \
    for [COL=STARTCOL:ENDCOL] 'npb.class.a.csv' u (column(0)-1+BOXWIDTH*(COL-STARTCOL+GAPSIZE/2+1)-0.5):COL:COL notitle w labels

