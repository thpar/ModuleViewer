To load data manually into a [new ModuleViewer session][new_session], download all files of a data set and load them one by one into ModuleViewer.

Start with the "Data Matrix" and the "Gene Modules". Then load "Symbolic Name Mapping", "Condition Modules", "Regulator Modules" and "Annotations" if applicable.

## Hughratcur ###

240 modules of yeast expression data. Hierarchical sub-clustering for both genes and conditions.

* [hughratcur_nan_filtered][hr_data]: expression data matrix
* [hughratcur_nan_filtered_geneTree.xml][hr_genes]: Gene tree, linking genes to modules and organizing them into a tree structure
* [hughratcur_nan_filtered_conditionTree.xml][hr_conditions]: Condition tree, linking conditions to modules and organizing them into a tree structure
* [hughratcur_nan_filtered_regulatorTree.xml][hr_regulators]: Regulator tree, linking regulators to modules and organizing them into a tree structure
* [Annotations.mvf][hr_annotations]: Integrated data (core genes, GO annotations, gene-gene interactions, ...)

[Java Webstart with pre-loaded data](http://bioinformatics.psb.ugent.be/webtools/moduleviewer/launch.jnlp?expmatrix=http://bioinformatics.psb.ugent.be/webtools/moduleviewer/testdata/hughratcur/hughratcur_nan_filtered&modules=http://bioinformatics.psb.ugent.be/webtools/moduleviewer/testdata/hughratcur/hughratcur_nan_filtered_geneTree.xml&conditions=http://bioinformatics.psb.ugent.be/webtools/moduleviewer/testdata/hughratcur/hughratcur_nan_filtered_conditionTree.xml&regulators=http://bioinformatics.psb.ugent.be/webtools/moduleviewer/testdata/hughratcur/hughratcur_nan_filtered_regulatorTree.xml&annotations=http://bioinformatics.psb.ugent.be/webtools/moduleviewer/testdata/hughratcur/Annotations.mvf)

[new_session]: http://bioinformatics.psb.ugent.be/webtools/moduleviewer/launch.jnlp


[hr_data]: <http://bioinformatics.psb.ugent.be/webtools/moduleviewer/testdata/hughratcur/hughratcur_nan_filtered>
[hr_genes]: <http://bioinformatics.psb.ugent.be/webtools/moduleviewer/testdata/hughratcur/hughratcur_nan_filtered_geneTree.xml>
[hr_conditions]: <http://bioinformatics.psb.ugent.be/webtools/moduleviewer/testdata/hughratcur/hughratcur_nan_filtered_conditionTree.xml>
[hr_regulators]: <http://bioinformatics.psb.ugent.be/webtools/moduleviewer/testdata/hughratcur/hughratcur_nan_filtered_regulatorTree.xml>
[hr_annotations]: <http://bioinformatics.psb.ugent.be/webtools/moduleviewer/testdata/hughratcur/Annotations.mvf>


## Abiotic stress in Arabidopsis Thaliana

Data to generate the figures for:  
Vermeirssen, V., De Clercq, I., Van Parys, T., Van Breusegem, F., Van de Peer, Y. (2014) [Arabidopsis ensemble reverse-engineered gene regulatory network discloses interconnected transcription factors in oxidative stress][paper]. *The Plant Cell* 26, 4656-4679

[paper]: http://bioinformatics.psb.ugent.be/supplementary_data/vamei/module_display/

* [ATH1stress_expr][ath_data]: expression data matrix
* [kmclust600_2014][ath_genes]: List of genes ordered into 600 modules
* [kmclust600_2014_reg][ath_regulators]: predicted regulator for each module
* [ath_symbol_2013][ath_symbols]: gene ID to symbolic name mapping for Arabidopsis
* [annotations][ath_annotations]: Integrated data (GO annotations, gene-gene interactions, known regulators, ...)

[Java Webstart with pre-loaded data](http://bioinformatics.psb.ugent.be/webtools/moduleviewer/launch.jnlp?expmatrix=http://bioinformatics.psb.ugent.be/webtools/moduleviewer/testdata/abiotic_stress/ATH1stress_expr&modules=http://bioinformatics.psb.ugent.be/webtools/moduleviewer/testdata/abiotic_stress/kmclust600_2014&regulators=http://bioinformatics.psb.ugent.be/webtools/moduleviewer/testdata/abiotic_stress/kmclust600_2014_reg&annotations=http://bioinformatics.psb.ugent.be/webtools/moduleviewer/testdata/abiotic_stress/annotations.mvf&synonyms=http://bioinformatics.psb.ugent.be/webtools/moduleviewer/testdata/abiotic_stress/ath_symbol_2013)


[ath_data]: <http://bioinformatics.psb.ugent.be/webtools/moduleviewer/testdata/abiotic_stress/ATH1stress_expr>
[ath_genes]: <http://bioinformatics.psb.ugent.be/webtools/moduleviewer/testdata/abiotic_stress/kmclust600_2014>
[ath_regulators]: <http://bioinformatics.psb.ugent.be/webtools/moduleviewer/testdata/abiotic_stress/kmclust600_2014_reg>
[ath_symbols]: <http://bioinformatics.psb.ugent.be/webtools/moduleviewer/testdata/abiotic_stress/ath_symbol_2013>
[ath_annotations]: <http://bioinformatics.psb.ugent.be/webtools/moduleviewer/testdata/abiotic_stress/annotations.mvf>
