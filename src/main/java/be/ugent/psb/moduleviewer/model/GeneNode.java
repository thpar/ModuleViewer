package be.ugent.psb.moduleviewer.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import be.ugent.psb.modulegraphics.elements.ITreeNode;

public class GeneNode implements ITreeNode<Gene>{
	/**
	 * Left child
	 */
	private GeneNode left;
	/**
	 * Right child
	 */
	private GeneNode right;
	
	/**
	 * True is this node doesn't have any children.
	 */
	private boolean isLeaf = false;
	
	/**
	 * Parent node, for navigating the tree upwards.
	 */
	private GeneNode parent;
	
	
	/**
	 * The genes grouped into this part of the tree.
	 * This attribute is not null only for leave nodes. Internal nodes just
	 * take the concatenation of the conditions of their children.
	 */
	private List<Gene> genes = new ArrayList<Gene>();
	
	private double sigma;
	private double mean;
	private boolean dataChanged = true;
	
	public GeneNode() {
	}

	public GeneNode(GeneNode parent) {
		this.parent = parent;
	}

	public GeneNode getParent() {
		return parent;
	}

	public void setLeft(GeneNode left) {
		this.left = left;
	}

	public void setRight(GeneNode right) {
		this.right = right;
	}
	
	/**
	 * Same as {@link getColumns}
	 * @return
	 */
	public List<Gene> getGenes() {
		return getColumns();
	}

	public void setGenes(List<Gene> genes) {
		this.genes = genes;
	}

	@Override
	public ITreeNode<Gene> left() {
		return left;
	}

	@Override
	public ITreeNode<Gene> right() {
		return right;
	}

	@Override
	public boolean isLeaf() {
		return isLeaf;
	}

	@Override
	public int getWidth() {
		if (isLeaf) return genes.size();
		else return left.getWidth() + right.getWidth();
	}

	@Override
	public List<Gene> getColumns() {
		if (isLeaf) return genes;
		else {
			List<Gene> cols = new ArrayList<Gene>();
			cols.addAll(left.getColumns());
			cols.addAll(right.getColumns());
			return cols;
		}
	}
	
	
	/**
	 * Get the gene in the given columns, counted over all nodes.
	 * 
	 * @param geneNumber
	 * @return
	 */
	public Gene getGene(int geneNumber){
		return getColumns().get(geneNumber);
	}
	
	/**
	 * 
	 * @param gene
	 * @return the first occurrence of the given gene in this list.
	 * @throws {@link GeneNotFoundException} if the gene is not in this list.
	 */
	public int getGeneLocation(Gene gene) throws GeneNotFoundException{
		List<Gene> list = getColumns();
		int index = list.indexOf(gene);			
		if (index >= 0){
			return index;
		} else {
			throw new GeneNotFoundException(gene);
		}
	}

	
	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	@Override
	public List<ITreeNode<Gene>> getLeaves() {
		List<ITreeNode<Gene>> list = new ArrayList<ITreeNode<Gene>>();
		getLeaves(list);
		return list;
	}
	
	private void getLeaves(List<ITreeNode<Gene>> list){
		if (isLeaf()) {
			list.add(this);
			return;
		} else {
			left.getLeaves(list);
			right.getLeaves(list);
			return;
		}		
	}

	public void addGene(Gene gene) {
		this.genes.add(gene);
	}
	
	
	public double getSigma(){
		if (dataChanged){
			calculateStats();
			dataChanged = false;
		}
		return this.sigma;
	}
	public double getMean(){
		if (dataChanged){
			calculateStats();
			dataChanged = false;
		}
		return this.mean;
	}
	
	
	/**
	 * 
	 * @param tree
	 * @return [mean, sigma]
	 */
	private void calculateStats() {
		double nrDataPoints = 0;
		double sumSquare = 0.0;
		this.mean = 0.0;
		this.sigma = 0.0;
			for (Gene gene : this.getGenes()){
				for (double value : gene.getData()) {
					if (!Double.isNaN(value)) {
						mean += value;
						sumSquare += Math.pow(value, 2);
						nrDataPoints++;
					}
				}
			}
		mean /= (double) nrDataPoints;
		sigma = Math.sqrt(sumSquare - nrDataPoints * Math.pow(mean, 2)) / Math.sqrt((double) nrDataPoints);
	}
	
	/**
	 * Comparator to order genes alphabetically. 
	 * For ordering, the alias is used. If no alias is available, the gene id is used.
	 */
	private class GeneComparator implements Comparator<Gene>{
		@Override
		public int compare(Gene o1, Gene o2) {
			return o1.getAliasOrName().compareTo(o2.getAliasOrName());
		}
		
	}
	
	/**
	 * Traverse the tree recursively and sort the genes in the leaves alphabetically.
	 * For ordering, the alias is used. If no alias is available, the gene id is used.
	 */
	public void sortLeavesAlphabetically(){
		sortLeavesAlphabetically(this);
	}
	private void sortLeavesAlphabetically(GeneNode node){
		if(node.isLeaf){
			Collections.sort(node.genes, new GeneComparator());
		} else {
			sortLeavesAlphabetically(node.left);
			sortLeavesAlphabetically(node.right);
		}
	}
	
}
