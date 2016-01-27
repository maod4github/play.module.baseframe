package baseframe.xml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import baseframe.enums.CharsetEnum;
import baseframe.helpers.StringHelper;

/**
 * XML解析者
 * <br><b>作者 : </b>maodun
 * <br><b>创建时间 : </b>2015年10月30日,下午5:29:24
 */
public class XMLParser {

	private XMLParser() {}

	/**
	 * 解析
	 * <br><b>作者 : </b>maodun
	 * <br><b>创建时间 : </b>2015年10月30日,下午5:29:13
	 * @param xml_str
	 * @return the docu
	 */
	public static XMLDocument parse(String xml_str) {
		List<XMLNode> nodes = new ArrayList<XMLNode>();
		XMLDocument document = new XMLDocument(nodes);
		parseNode(xml_str, nodes);
		return document;
	}
	
	/**
	 * 解析
	 * <br><b>作者 : </b>maodun
	 * <br><b>创建时间 : </b>2015年10月30日,下午5:57:05
	 * @param by_file_path
	 * @param by_charset
	 * @return the docu or null
	 */
	public static XMLDocument parse(String by_file_path, CharsetEnum by_charset) {
		try {
			return parse(new File(by_file_path), by_charset);
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 根据文件对象与指定的字符集解析XML文档
	 * <br><b>作者 : </b>maodun
	 * <br><b>创建时间 : </b>2015年10月30日,下午5:01:48
	 * @param file 文件对象
	 * @param charset 字符集
	 * @return the docu or null
	 */
	public static XMLDocument parse(File file, CharsetEnum charset) {
		try {
			return parse(new FileInputStream(file), charset);
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 根据输入流与指定的字符集解析XML文档
	 * <br><b>作者 : </b>maodun
	 * <br><b>创建时间 : </b>2015年10月30日,下午5:00:16
	 * @param inputStream 输入流
	 * @param charset 字符集
	 * @return the docu or null
	 */
	public static XMLDocument parse(InputStream inputStream, CharsetEnum charset) {
		try {
			long beginTime = System.currentTimeMillis();
			dugPrint("parse begin time:" + beginTime);
			List<XMLNode> nodes = new ArrayList<XMLNode>();
			XMLDocument document = new XMLDocument(nodes);
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, charset.getVal()));
			StringBuffer sb = new StringBuffer("");
			String str = null;
			while ((str = br.readLine()) != null) {
				sb.append(str);
			}
			br.close();
			parseNode(sb.toString(), nodes);
			int parentIsNullCount = 0;
			for (XMLNode n : nodes) {
				if (n.getParent() == null) {
					parentIsNullCount++;
				}
			}
			if (parentIsNullCount != 1) {
				throw new Exception("无效的XML文件，没有根节点");
			}
			long endTime = System.currentTimeMillis();
			dugPrint("parse end time:" + endTime);
			dugPrint("parse spend time:" + (endTime - beginTime));
			return document;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 解析节点
	 * <br><b>作者 : </b>maodun
	 * <br><b>创建时间 : </b>2015年10月30日,下午5:06:22
	 * @param nodeStr 节点字符串
	 * @param nodes 节点集 - 解析的节点会装入此集合
	 */
	private static void parseNode(String nodeStr, List<XMLNode> nodes) {
		try {
			dugPrint("fomat before nodeStr:" + nodeStr);
			
			nodeStr = nodeStr
				.replaceAll("\\s+", " ") // 1个或多个空字符替换成1个空字符
				.replaceAll("\\<\\s+", "<") // 去掉左尖括号右边的空字符
				.replaceAll("\\s+\\>", ">") // 去掉右尖括号左边的空字符
				.replaceAll("\\>\\s+\\<", "><") // 去掉右尖括号与左尖括号之间的空字符
				.replaceAll("\\s+=\\s+", "=") // 去掉等号两边的空字符
				.replaceAll("\\=\"\\s+", "=\"") // 去掉等号+双引号右边的空字符
				.replaceAll("\\s+\"", "\"") // 去掉双引号左边空字符
				.replaceAll("(\\<)([^\\>&&\\S]*)(\\s?)([^\\<]*)(\\/\\>)", "$1$2$3$4></$2>") // 替换所有简单结束标记<nodeName .../>为完整结束标记</nodeName>
				.trim();// 去掉前后空字符
	
			nodeStr = StringHelper.replaceAll(nodeStr, "<?", "?>", "");// 去掉以<?开头并且以?>结尾的节点
			nodeStr = StringHelper.replaceAll(nodeStr, "<!--", "-->", "");// 去掉注释节点
			nodeStr = StringHelper.replaceAllWithInnerStr(nodeStr, "<![CDATA[", "]]>");// 处理<![CDATA[]]节点
			
			dugPrint("fomat after nodeStr:" + nodeStr);
			
			if (!StringHelper.isValid(nodeStr)) {
				throw new Exception("无效的XML文件，没有根节点");
			}
			
			XMLNode node = new XMLNode();
			String nodeName = nodeStr.substring(nodeStr.indexOf("<") + 1, nodeStr.indexOf(">")).trim();
			String attributeStr = "";
			if (nodeName.indexOf(" ") != -1) {
				attributeStr = nodeName.substring(nodeName.indexOf(" ")).trim();
				dugPrint("attributeStr:" + attributeStr);
				String[] attributes = attributeStr.split("\"\\s");
				for (int i = 0; i < attributes.length - 1; i++) {
					attributes[i] += "\"";
				}
				Map<String, String> attributeKVs = new HashMap<String, String>();
				for (String attribute : attributes) {
					dugPrint("attribute:" + attribute);
					String[] kv = attribute.split("\\=\"");
					kv[1] = "\"" + kv[1];
					dugPrint("key:" + kv[0] + ", value:" + kv[1]);
					if (kv.length != 2) {
						throw new Exception("无效的XML文件，属性键值定义非法或缺失");
					}
					if (kv[1].substring(1, kv[1].length() - 1).indexOf("\"") != -1) {
						throw new Exception("无效的XML文件，属性值有多余双引号");
					}
					attributeKVs.put(kv[0].trim(), kv[1].trim().replaceAll("\"", ""));
					node.setAttributeKVs(attributeKVs);
				}
				nodeName = nodeName.substring(0, nodeName.indexOf(" "));
			}
			dugPrint("nodeName:" + nodeName);
			node.setName(nodeName.trim());
			if (nodeStr.indexOf("</" + nodeName + ">") == -1) {
				throw new Exception("无效的XML文件，" + nodeName + "节点找不到匹配的结束标记");
			}
			String childNodeStr = nodeStr.substring(
					nodeStr.indexOf("<" + nodeName
							+ ("".equals(attributeStr) ? "" : " " + attributeStr)
							+ ">")
							+ nodeName.length()
							+ attributeStr.length()
							+ ("".equals(attributeStr) ? 2 : 3),
					nodeStr.indexOf("</" + nodeName + ">"));
			dugPrint("childNodeStr:" + childNodeStr);
			if (childNodeStr.indexOf("<" + nodeName + " ") != -1 || childNodeStr.indexOf("<" + nodeName + ">") != -1) {
				throw new Exception("无效的XML文件，同名节点" + nodeName + "有内嵌");
			}
			node.setContent(childNodeStr.trim());
			if (childNodeStr.indexOf("<") != -1) {
				List<XMLNode> descendants = new ArrayList<XMLNode>();
				parseNode(childNodeStr, descendants);
				List<XMLNode> childs = new ArrayList<XMLNode>();
				childs.addAll(descendants);
				for (Iterator<XMLNode> iterator = childs.iterator(); iterator.hasNext();) {
					XMLNode n = iterator.next();
					if (n.getParent() == null) {
						n.setParent(node);
					}
					else {
						iterator.remove();
					}
				}
				node.setChilds(childs);
				nodes.addAll(descendants);
			}
			nodes.add(node);
			nodeStr = nodeStr.substring(nodeStr.indexOf("</" + nodeName + ">") + nodeName.length() + 3).trim();
			if (!"".equals(nodeStr)) {
				parseNode(nodeStr, nodes);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 调试打印
	 * <br><b>作者 : </b>maodun
	 * <br><b>创建时间 : </b>2015年10月30日,下午4:54:27
	 * @param msg 打印消息
	 */
	private static void dugPrint(String msg) {
//		System.out.println(msg);
	}

}
