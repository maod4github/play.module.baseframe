package baseframe.xml;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * XML文档
 * <br><b>作者 : </b>maodun
 * <br><b>创建时间 : </b>2015年10月30日,下午5:11:54
 */
public class XMLDocument {

	private List<XMLNode> nodes;

	public XMLDocument(List<XMLNode> nodes) {
		this.nodes = nodes;
	}
	
	/**
	 * 获取根节点
	 * <br><b>作者 : </b>maodun
	 * <br><b>创建时间 : </b>2015年10月30日,下午5:11:40
	 * @return 根节点
	 */
	public XMLNode getRootNode() {
		for (XMLNode node : this.nodes) {
			if (node.getParent() == null) {
				return node;
			}
		}
		return null;
	}

	/**
	 * 获取所有节点
	 * <br><b>作者 : </b>maodun
	 * <br><b>创建时间 : </b>2015年10月30日,下午5:11:20
	 * @return 所有节点
	 */
	public List<XMLNode> getNodes() {
		return this.nodes;
	}

	/**
	 * 根据节点名获取节点集
	 * <br><b>作者 : </b>maodun
	 * <br><b>创建时间 : </b>2015年10月30日,下午5:11:07
	 * @param name 节点名
	 * @return 节点名等于name的节点集
	 */
	public List<XMLNode> getNodes(String name) {
		List<XMLNode> nodes = new ArrayList<XMLNode>();
		for (Iterator<XMLNode> iterator = this.nodes.iterator(); iterator
				.hasNext();) {
			XMLNode node = iterator.next();
			if (name.equals(node.getName()))
				nodes.add(node);
		}
		return nodes;
	}

	/**
	 * 根据节点名和服节点名获取节点集
	 * <br><b>作者 : </b>maodun
	 * <br><b>创建时间 : </b>2015年10月30日,下午5:10:48
	 * @param name 节点名
	 * @param parentName 父节点名
	 * @return 节点名等于name，并且父节点名等于parentName的节点集
	 */
	public List<XMLNode> getNodes(String name, String parentName) {
		List<XMLNode> nodes = new ArrayList<XMLNode>();
		for (Iterator<XMLNode> iterator = this.nodes.iterator(); iterator
				.hasNext();) {
			XMLNode node = iterator.next();
			if (name.equals(node.getName())
					&& parentName.equals(node.getParent().getName()))
				nodes.add(node);
		}
		return nodes;
	}

	/**
	 * 根据属性键值对获取节点集
	 * <br><b>作者 : </b>maodun
	 * <br><b>创建时间 : </b>2015年10月30日,下午5:10:33
	 * @param key 属性键
	 * @param value 属性值
	 * @return 属性键等于key，且属性值等于value的节点集
	 */
	public List<XMLNode> getNodesByAttributeKV(String key, String value) {
		List<XMLNode> nodes = new ArrayList<XMLNode>();
		for (Iterator<XMLNode> iterator = this.nodes.iterator(); iterator
				.hasNext();) {
			XMLNode node = iterator.next();
			Map<String, String> attributeKVs = node.getAttributeKVs();
			if (attributeKVs != null && value.equals(attributeKVs.get(key)))
				nodes.add(node);
		}
		return nodes;
	}

	/**
	 * 根据节点名与属性键值对获取节点集
	 * <br><b>作者 : </b>maodun
	 * <br><b>创建时间 : </b>2015年10月30日,下午5:10:14
	 * @param name 节点名
	 * @param key 属性键
	 * @param value 属性值
	 * @return 节点名等于name，且属性键等于key，且属性值等于value的节点集
	 */
	public List<XMLNode> getNodesByNameAndAttributeKV(String name, String key,
			String value) {
		List<XMLNode> nodes = new ArrayList<XMLNode>();
		for (Iterator<XMLNode> iterator = this.nodes.iterator(); iterator
				.hasNext();) {
			XMLNode node = iterator.next();
			Map<String, String> attributeKVs = node.getAttributeKVs();
			if (name.equals(node.getName()) && attributeKVs != null
					&& value.equals(attributeKVs.get(key)))
				nodes.add(node);
		}
		return nodes;
	}

	/**
	 * 获取没有属性的节点
	 * <br><b>作者 : </b>maodun
	 * <br><b>创建时间 : </b>2015年10月30日,下午5:10:03
	 * @return 没有属性的节点集
	 */
	public List<XMLNode> getNotHaveAttributeNodes() {
		List<XMLNode> nodes = new ArrayList<XMLNode>();
		for (Iterator<XMLNode> iterator = this.nodes.iterator(); iterator
				.hasNext();) {
			XMLNode node = iterator.next();
			Map<String, String> attributeKVs = node.getAttributeKVs();
			if (attributeKVs == null)
				nodes.add(node);
		}
		return nodes;
	}

	/**
	 * 根据节点名获取没有属性的节点
	 * <br><b>作者 : </b>maodun
	 * <br><b>创建时间 : </b>2015年10月30日,下午5:09:38
	 * @param name 节点名
	 * @return 节点名等于name，且没有属性的节点集
	 */
	public List<XMLNode> getNotHaveAttributeNodes(String name) {
		List<XMLNode> nodes = new ArrayList<XMLNode>();
		for (Iterator<XMLNode> iterator = this.nodes.iterator(); iterator
				.hasNext();) {
			XMLNode node = iterator.next();
			Map<String, String> attributeKVs = node.getAttributeKVs();
			if (name.equals(node.getName()) && attributeKVs == null)
				nodes.add(node);
		}
		return nodes;
	}

	/**
	 * 获取叶子节点集
	 * <br><b>作者 : </b>maodun
	 * <br><b>创建时间 : </b>2015年10月30日,下午5:09:12
	 * @return 子节点集为空的节点集
	 */
	public List<XMLNode> getLeafNodes() {
		List<XMLNode> nodes = new ArrayList<XMLNode>();
		for (Iterator<XMLNode> iterator = this.nodes.iterator(); iterator.hasNext();) {
			XMLNode node = iterator.next();
			List<XMLNode> childs = node.getChilds();
			if (childs == null || childs.isEmpty()) {
				nodes.add(node);
			}
		}
		return nodes;
	}

	/**
	 * 获取非叶子节点集
	 * <br><b>作者 : </b>maodun
	 * <br><b>创建时间 : </b>2015年10月30日,下午5:08:56
	 * @return 子节点不为空的节点集
	 */
	public List<XMLNode> getNotLeafNodes() {
		List<XMLNode> nodes = new ArrayList<XMLNode>();
		for (Iterator<XMLNode> iterator = this.nodes.iterator(); iterator.hasNext();) {
			XMLNode node = iterator.next();
			List<XMLNode> childs = node.getChilds();
			if (childs != null && !childs.isEmpty()) {
				nodes.add(node);
			}
		}
		return nodes;
	}
	
	/**
	 * 获取第一个节点
	 * <br><b>作者 : </b>maodun
	 * <br><b>创建时间 : </b>2015年10月30日,下午7:49:13
	 * @param by_node_name
	 * @return the obj or null
	 */
	public XMLNode getFirst(String by_node_name) {
		List<XMLNode> nodes = this.getNodes(by_node_name);
		if (nodes == null || nodes.isEmpty()) {
			return null;
		}
		return nodes.get(0);
	}

}
