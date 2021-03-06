set terminal pngcairo enhanced font "arial,10" fontscale 1.0 size 500, 240
set bar 1.000000 front
set boxwidth 0.9 absolute
set style fill   solid 1.00 border lt -1
set style circle radius graph 0.02, first 0.00000, 0.00000 
set style ellipse size graph 0.05, 0.03, first 0.00000 angle 0 units xy
set ylabel "Completion Time (seconds)"
set yrange [0:*]
GAPSIZE=2
set style histogram cluster gap 2
STARTCOL=2                 #Start plotting data in this column (2 for your example)
ENDCOL=6                   #Last column of data to plot (10 for your example)
NCOL=ENDCOL-STARTCOL+1     #Number of columns we're plotting 
BOXWIDTH=1./(GAPSIZE+NCOL) #Width of each box.

set output 'CGvA.png'
plot for [COL=STARTCOL:ENDCOL] 'CG.A.csv' u COL:xtic(1) w histogram title columnheader(COL), \
    for [COL=STARTCOL:ENDCOL] 'CG.A.csv' u (column(0)-1+BOXWIDTH*(COL-STARTCOL+GAPSIZE/2+1)-0.5):COL:COL notitle w labels

set output 'EPvA.png'
plot for [COL=STARTCOL:ENDCOL] 'EP.A.csv' u COL:xtic(1) w histogram title columnheader(COL), \
    for [COL=STARTCOL:ENDCOL] 'EP.A.csv' u (column(0)-1+BOXWIDTH*(COL-STARTCOL+GAPSIZE/2+1)-0.5):COL:COL notitle w labels

set output 'FTvA.png'
plot for [COL=STARTCOL:ENDCOL] 'FT.A.csv' u COL:xtic(1) w histogram title columnheader(COL), \
    for [COL=STARTCOL:ENDCOL] 'FT.A.csv' u (column(0)-1+BOXWIDTH*(COL-STARTCOL+GAPSIZE/2+1)-0.5):COL:COL notitle w labels

set output 'ISvA.png'
plot for [COL=STARTCOL:ENDCOL] 'IS.A.csv' u COL:xtic(1) w histogram title columnheader(COL), \
    for [COL=STARTCOL:ENDCOL] 'IS.A.csv' u (column(0)-1+BOXWIDTH*(COL-STARTCOL+GAPSIZE/2+1)-0.5):COL:COL notitle w labels

set output 'LUvA.png'
plot for [COL=STARTCOL:ENDCOL] 'LU.A.csv' u COL:xtic(1) w histogram title columnheader(COL), \
    for [COL=STARTCOL:ENDCOL] 'LU.A.csv' u (column(0)-1+BOXWIDTH*(COL-STARTCOL+GAPSIZE/2+1)-0.5):COL:COL notitle w labels

set output 'MGvA.png'
plot for [COL=STARTCOL:ENDCOL] 'MG.A.csv' u COL:xtic(1) w histogram title columnheader(COL), \
    for [COL=STARTCOL:ENDCOL] 'MG.A.csv' u (column(0)-1+BOXWIDTH*(COL-STARTCOL+GAPSIZE/2+1)-0.5):COL:COL notitle w labels



set output 'CGvB.png'
plot for [COL=STARTCOL:ENDCOL] 'CG.B.csv' u COL:xtic(1) w histogram title columnheader(COL), \
    for [COL=STARTCOL:ENDCOL] 'CG.B.csv' u (column(0)-1+BOXWIDTH*(COL-STARTCOL+GAPSIZE/2+1)-0.5):COL:COL notitle w labels


set output 'EPvB.png'
plot for [COL=STARTCOL:ENDCOL] 'EP.B.csv' u COL:xtic(1) w histogram title columnheader(COL), \
    for [COL=STARTCOL:ENDCOL] 'EP.B.csv' u (column(0)-1+BOXWIDTH*(COL-STARTCOL+GAPSIZE/2+1)-0.5):COL:COL notitle w labels


set output 'FTvB.png'
plot for [COL=STARTCOL:ENDCOL] 'FT.B.csv' u COL:xtic(1) w histogram title columnheader(COL), \
    for [COL=STARTCOL:ENDCOL] 'FT.B.csv' u (column(0)-1+BOXWIDTH*(COL-STARTCOL+GAPSIZE/2+1)-0.5):COL:COL notitle w labels


set output 'ISvB.png'
plot for [COL=STARTCOL:ENDCOL] 'IS.B.csv' u COL:xtic(1) w histogram title columnheader(COL), \
    for [COL=STARTCOL:ENDCOL] 'IS.B.csv' u (column(0)-1+BOXWIDTH*(COL-STARTCOL+GAPSIZE/2+1)-0.5):COL:COL notitle w labels


set output 'LUvB.png'
plot for [COL=STARTCOL:ENDCOL] 'LU.B.csv' u COL:xtic(1) w histogram title columnheader(COL), \
    for [COL=STARTCOL:ENDCOL] 'LU.B.csv' u (column(0)-1+BOXWIDTH*(COL-STARTCOL+GAPSIZE/2+1)-0.5):COL:COL notitle w labels


set output 'MGvB.png'
plot for [COL=STARTCOL:ENDCOL] 'MG.B.csv' u COL:xtic(1) w histogram title columnheader(COL), \
    for [COL=STARTCOL:ENDCOL] 'MG.B.csv' u (column(0)-1+BOXWIDTH*(COL-STARTCOL+GAPSIZE/2+1)-0.5):COL:COL notitle w labels








