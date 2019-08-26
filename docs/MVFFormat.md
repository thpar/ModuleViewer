## ModuleViewer Format #########

*Annotations*, either for genes or for conditions, are grouped into *blocks*. Each block consist of a certain type of annotation that will be drawn together. An Annotation is one line that can be interpreted as "This module has a list of items that are annotated with this property".

The header of a block starts each line with `::` and consists of `key=value` pairs.

Possible keys:
* **TYPE**: this tells the viewer how to treat this block. The type will also be used as a default label for the block.  

    By default, annotation blocks are drawn as colored matrices, so TYPE can be any value describing the type of data represented here (go, TF enrichment, core, etc, ...).  

    A few types are reserved words, which will be represented differently:  

    Blocks that highlight genes or regulators names:
    * genecolorbox
    * regulatorcolorbox
  
    Blocks that are drawn as edges on the left side of the figure:
    * regulator-regulator interaction
    * regulator-gene interaction
    * gene-gene interaction

  

* **TITLE**: Optional label for the block.
* **OBJECT**: `GENES`, `CONDITIONS` or `REGULATORS`, depending on what you're annotating. `GENE` and `REGULATORS` blocks will be drawn right of the expression matrix. `CONDITIONS` blocks are drawn below it.
* **COLOR**: The color to use when drawing.
* **VALUES**: Either `None` (or just omit the `VALUES` line), `COLOR` or `NUMBER`, depending on what extra information is linked to the individual genes.
* **GLOBAL**: Allows you to drop the first column (module id). Annotations will be drawn the same for every module.
* **VALUE_SEPARATOR**: Changes the default key-value separator ':' to something else. Useful when item names contain ':' themselves.
* **LEGEND**: Constructs a Legend block below the figure. The value of this key is constructed like any annotation line (see example)

Format:

`ModuleId TAB Gene list (pipe delimited) TAB Annotation name (optional)`

The genes in the gene list can be linked to a value (color or number, as set in the VALUES header) as `Gene:value` pairs.

Example:
```
::TYPE=reg_rank
::TITLE=Regulator ranking
::VALUES=color
::OBJECT=REGULATORS
101     AT3G22780:#CCCCFF|AT4G00130:#DADAFF     ranking
101     AT3G22780:#FF0000|AT4G00130:#FFEAEA     percentage
```
This small block will draw an annotation block next to the regulators of module 101. It will have as many rows as there are regulators and will have two columns: ranking and percentage.
In the ranking column, the regulators `AT3G22780` and `AT4G00130` will be marked with a shade of blue. In the percentage column, `AT3G22780` and `AT4G00130` are marked with shades of red.
Color codes can be defined by name, RGB code or Java color int.


Example:
```
::VALUE_SEPARATOR=//
::LEGEND=SA+//MAGENTA|ABA+//RED|MJ+//YELLOW|BR-//CYAN|BR+//ORANGE|ET+//GRAY|AUXIN-//GREEN|AUXIN+//BLUE|ET-//BLACK       hormones
```
This will add a legend to this block with the label 'hormones'. Hormones SA+, ABA+, BR-, etc... are respectively shown with the colors magenta, red, yellow, ...
