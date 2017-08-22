#!/bin/bash

for fig in 5 20 50; do
	for topo in global local; do
		for param in decay c1c2; do

			echo -e "\\ begin{figure}[H]"
			echo -e "\t\\ centering"
			echo -e "\t\\ includegraphics[width=.8\\ textwidth]{img/result_"$fig"_"$topo"_"$param".png}"
			echo -e "\t\\ caption{Boxplot para configura\\ c{c}\\~ao "$fig"-"$topo"-"$param"}"
			echo -e "\t\\ label{fig:result_"$fig"_"$topo"_"$param"}"
			echo -e "\\ end{figure}"
			echo -e ""

		done
	done
done

# OBS:
#  Na saída executar o comando:
#    sed -e 's/\\ /\\/g'
