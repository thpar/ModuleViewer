# XML Format ####

Gene and regulator input files can be formatted as [simple lists](ListFile.md).  
However, to represent subclustering in a tree structure, XML can be used.

The parent element is `ModuleNetwork`, it's children being `Module`s with an `id` attribute and an optional `name` attribute.
A module has a `GeneTree` which contains a tree structure of `Child` elements, either with `node` attribute set to `internal` or `leaf`. Multiple `leaf` children should be bundled into an `internal` child.  
Each child has an ordered set of `Gene` elements. A gene has a `name` attribute and an optional `alias` (symbolic name). Symbolic names could also be loaded from a simple mapping file at a later stage.

Regulator data is formatted in a similar way, but uses `RegulatorTree` elements instead of `GeneTree`.

Don't forget the XML declaration at the beginning of the file, to tell ModuleViewer the XML-parser should be used.

Example:
```
<?xml version='1.0' encoding='iso-8859-1'?>
<ModuleNetwork>
  <Module id="76" name="Cluster 76">
    <GeneTree>
      <Child node="leaf">
	<Gene name="YHR148W" alias="IMP3"/>
	<Gene name="YDR184C" alias="ATC1"/>
	<Gene name="YLR146C" alias="SPE4"/>
	<Gene name="YCR035C" alias="RRP43"/>
	<Gene name="YJR041C" alias="URB2"/>
	<Gene name="YKL014C" alias="URB1"/>
	<Gene name="YMR229C" alias="RRP5"/>
	<Gene name="YPR060C" alias="ARO7"/>
      </Child>
    </GeneTree>
  </Module>
  <Module id="24" name="YMR292W">
    <GeneTree>
      <Child node="internal">
	<Child node="leaf">
	  <Gene name="YJL091C" alias="GWT1"/>
	</Child>
	<Child node="internal">
	  <Child node="leaf">
	    <Gene name="YDR222W"/>
	  </Child>
	  <Child node="leaf">
	    <Gene name="YMR076C" alias="PDS5"/>
	    <Gene name="YBL003C" alias="HTA2"/>
	    <Gene name="YPR119W" alias="CLB2"/>
	    <Gene name="YPL279C"/>
	    <Gene name="YDR097C" alias="MSH6"/>
	    <Gene name="YLR068W" alias="FYV7"/>
	    <Gene name="YML052W" alias="SUR7"/>
	    <Gene name="YPL227C" alias="ALG5"/>
	    <Gene name="YAL059W" alias="ECM1"/>
	    <Gene name="YLR049C"/>
	    <Gene name="YIL158W" alias="AIM20"/>
	    <Gene name="YDR302W" alias="GPI11"/>
	  </Child>
	</Child>
      </Child>
    </GeneTree>
  </Module>
</ModuleNetwork>

```


For "horizontal" subclustering (arranging a subset of conditions into a tree structure), one can use the `ConditionTree`.
For this data, an extra element `NonTreeConditions` is introduced, to enable the user to place some conditions outside of the tree structure.
The children of `Child` elements (and of `NonTreeConditions`) here are not `Gene` but `Condition`. Conditions ids are referencing to the position of
the conditions in the original expression data matrix. When specifying both a `name` and an `id` attribute, the `id` will be used.
```
<?xml version='1.0' encoding='iso-8859-1'?>
<ModuleNetwork>
  <Module id="206" name="YIL106W">
    <ConditionTree>
      <Child node="leaf">
	<Condition id="268"/>
      </Child>
    </ConditionTree>
  </Module>
  <Module id="23" name="YLR354C">
    <ConditionTree>
      <Child node="internal">
	<Child node="leaf">
	  <Condition id="126"/>
	  <Condition id="125"/>
	  <Condition id="254"/>
	  <Condition id="227"/>
	  <Condition id="188"/>
	  <Condition id="93"/>
	  <Condition id="122"/>
	</Child>
	<Child node="leaf">
	  <Condition id="272"/>
	  <Condition id="263"/>
	  <Condition id="267"/>
	  <Condition id="258"/>
	  <Condition id="53"/>
	  <Condition id="260"/>
	</Child>
      </Child>
    </ConditionTree>
    <NonTreeConditions>
      <Condition id="165"/>
      <Condition id="47"/>
      <Condition id="232"/>
    </NonTreeConditions>
  </Module>
</ModuleNetwork>
```
