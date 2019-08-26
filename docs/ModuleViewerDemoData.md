To load data manually into a [new ModuleViewer session][new_session], download all files of a data set and load them one by one into ModuleViewer.

Start with the "Data Matrix" and the "Gene Modules". Then load "Symbolic Name Mapping", "Condition Modules", "Regulator Modules" and "Annotations" if applicable.

## Hughratcur ###

240 modules of yeast expression data. Hierarchical sub-clustering for both genes and conditions.

* [hughratcur_nan_filtered][data]: expression data matrix
* [hughratcur_nan_filtered_geneTree.xml][genes]: Gene tree, linking genes to modules and organizing them into a tree structure
* [hughratcur_nan_filtered_conditionTree.xml][conditions]: Condition tree, linking conditions to modules and organizing them into a tree structure
* [hughratcur_nan_filtered_regulatorTree.xml][regulators]: Regulator tree, linking regulators to modules and organizing them into a tree structure
* [Annotations.mvf][annotations]: Integrated data (core genes, GO annotations, gene-gene interactions, ...)

[Java Webstart with pre-loaded data](http://bioinformatics.psb.ugent.be/webtools/moduleviewer/launch.jnlp?expmatrix=http://bioinformatics.psb.ugent.be/webtools/moduleviewer/testdata/hughratcur/hughratcur_nan_filtered&modules=http://bioinformatics.psb.ugent.be/webtools/moduleviewer/testdata/hughratcur/hughratcur_nan_filtered_geneTree.xml&conditions=http://bioinformatics.psb.ugent.be/webtools/moduleviewer/testdata/hughratcur/hughratcur_nan_filtered_conditionTree.xml&regulators=http://bioinformatics.psb.ugent.be/webtools/moduleviewer/testdata/hughratcur/hughratcur_nan_filtered_regulatorTree.xml&annotations=http://bioinformatics.psb.ugent.be/webtools/moduleviewer/testdata/hughratcur/Annotations.mvf)

[new_session]: http://bioinformatics.psb.ugent.be/webtools/moduleviewer/launch.jnlp


[data]: <http://bioinformatics.psb.ugent.be/webtools/moduleviewer/testdata/hughratcur/hughratcur_nan_filtered>
[genes]: <http://bioinformatics.psb.ugent.be/webtools/moduleviewer/testdata/hughratcur/hughratcur_nan_filtered_geneTree.xml>
[conditions]: <http://bioinformatics.psb.ugent.be/webtools/moduleviewer/testdata/hughratcur/hughratcur_nan_filtered_conditionTree.xml>
[regulators]: <http://bioinformatics.psb.ugent.be/webtools/moduleviewer/testdata/hughratcur/hughratcur_nan_filtered_regulatorTree.xml>
[annotations]: <http://bioinformatics.psb.ugent.be/webtools/moduleviewer/testdata/hughratcur/Annotations.mvf>


## Abiotic stress in Arabidopsis Thaliana

Data to generate the figures for:  
Vermeirssen, V., De Clercq, I., Van Parys, T., Van Breusegem, F., Van de Peer, Y. (2014) [Arabidopsis ensemble reverse-engineered gene regulatory network discloses interconnected transcription factors in oxidative stress][paper]. *The Plant Cell* 26, 4656-4679

[paper]: http://bioinformatics.psb.ugent.be/supplementary_data/vamei/module_display/

* [ATH1stress_expr][data]: expression data matrix
* [kmclust600_2014][genes]: List of genes ordered into 600 modules
* [kmclust600_2014_reg][regulators]: predicted regulator for each module
* [ath_symbol_2013][symbols]: gene ID to symbolic name mapping for Arabidopsis
* [annotations][]: Integrated data (GO annotations, gene-gene interactions, known regulators, ...)

[Java Webstart with pre-loaded data](http://bioinformatics.psb.ugent.be/webtools/moduleviewer/launch.jnlp?expmatrix=http://bioinformatics.psb.ugent.be/webtools/moduleviewer/testdata/abiotic_stress/ATH1stress_expr&modules=http://bioinformatics.psb.ugent.be/webtools/moduleviewer/testdata/abiotic_stress/kmclust600_2014&regulators=http://bioinformatics.psb.ugent.be/webtools/moduleviewer/testdata/abiotic_stress/kmclust600_2014_reg&annotations=http://bioinformatics.psb.ugent.be/webtools/moduleviewer/testdata/abiotic_stress/annotations.mvf&synonyms=http://bioinformatics.psb.ugent.be/webtools/moduleviewer/testdata/abiotic_stress/ath_symbol_2013)


[data]: <http://bioinformatics.psb.ugent.be/webtools/moduleviewer/testdata/abiotic_stress/ATH1stress_expr>
[genes]: <http://bioinformatics.psb.ugent.be/webtools/moduleviewer/testdata/abiotic_stress/kmclust600_2014>
[regulators]: <http://bioinformatics.psb.ugent.be/webtools/moduleviewer/testdata/abiotic_stress/kmclust600_2014_reg>
[symbols]: <http://bioinformatics.psb.ugent.be/webtools/moduleviewer/testdata/abiotic_stress/ath_symbol_2013>
[annotations]: <http://bioinformatics.psb.ugent.be/webtools/moduleviewer/testdata/abiotic_stress/annotations.mvf>
