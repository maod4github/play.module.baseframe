package baseframe.xml;

import java.util.List;
import java.util.Map;

/**
 * XML节点
 * <br><b>作者 : </b>maodun
 * <br><b>创建时间 : </b>2015年10月30日,下午5:12:49
 */
public class XMLNode {

	private String name;
	private Map<String, String> attributeKVs;
	private String cont;
	private XMLNode parent;
	private List<XMLNode> childs;

	public XMLNode() {}

	public XMLNode(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCont() {
		return cont;
	}

	public void setContent(String cont) {
		this.cont = cont;
	}

	public XMLNode getParent() {
		return parent;
	}

	public void setParent(XMLNode parent) {
		this.parent = parent;
	}

	public List<XMLNode> getChilds() {
		return childs;
	}

	public void setChilds(List<XMLNode> childs) {
		this.childs = childs;
	}

	public Map<String, String> getAttributeKVs() {
		return attributeKVs;
	}

	public void setAttributeKVs(Map<String, String> attributeKVs) {
		this.attributeKVs = attributeKVs;
	}

	public boolean isRoot() {
		return (this.getParent() == null);
	}

	public boolean isLeaf() {
		List<XMLNode> childs = this.getChilds();
		return childs == null || childs.isEmpty();
	}

	@Override
	public String toString() {
		return "XMLNode" + (this.isRoot() ? " (root)" : "")
			+ (this.isLeaf() ? " (leaf)" : "") + " [name=" + name
			+ ", attributeKVs=" + attributeKVs + ", cont=" + cont
			+ ", parent=" + (parent == null ? "null" : parent.getName())
			+ ", childs=" + childs + "]";
	}

}