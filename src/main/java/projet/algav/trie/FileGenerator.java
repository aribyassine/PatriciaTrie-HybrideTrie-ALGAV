package projet.algav.trie;

import java.io.*;

public class FileGenerator {
    private static void writer(String path, String arg) throws IOException {
        Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "utf-8"));
        writer.write(arg);
        writer.close();
    }

    public static boolean generate(Trie trie, String directory, String fileName) {
        String path = directory + fileName;
        try {
            writer(path + "_Collapsible.json", trie.toCollapsibleJSON().toString());
            writer(path + "_noCollapsible.json", trie.toNoCollapsibleJSON().toString());
            writer(path + "_Collapsible.html", HtmlCollapsible(trie, fileName));
            writer(path + "_noCollapsible.html", HtmlNoCollapsible(trie, fileName));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static String HtmlNoCollapsible(Trie trie, String fileName) {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "  <head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "\n" +
                "    <title>Collapsible Tree Example</title>\n" +
                "\n" +
                "    <style>\n" +
                "\n" +
                "body{\n" +
                "   font: 14px sans-serif;\n" +
                "}" +
                "span {\n" +
                "\n" +
                "padding-left:150px;\n" +
                "\n" +
                "}" +
                "  .node circle {\n" +
                "    fill: #fff;\n" +
                "    stroke: steelblue;\n" +
                "    stroke-width: 1.5px;\n" +
                "  }\n" +
                "\n" +
                "  .node text { font: 12px sans-serif; }\n" +
                "\n" +
                "  .link {\n" +
                "    fill: none;\n" +
                "    stroke: #ccc;\n" +
                "    stroke-width: 1.5px;\n" +
                "  }\n" +
                "  \n" +
                "    </style>\n" +
                "\n" +
                "  </head>\n" +
                "\n" +
                "  <body>\n" +
                "\n" +
                "Nombre de mot : " + trie.comptageMots() + "<span/>" +
                "Hauteur : " + trie.hauteur() + "<span/>" +
                "Nombre de pointeurs vers NIL : " + trie.comptageNil() + "<span/>" +
                "Profondeur moyenne des feuilles : " + trie.profondeurMoyenne() + "<span/>" +
                "<br/>" +
                "ListeMots : " + trie.listeMots() + "<br/>" +
                "<!-- load the d3.js library --> \n" +
                "<script src=\"http://d3js.org/d3.v3.min.js\"></script>\n" +
                "  \n" +
                "<script>\n" +
                "\n" +
                "// ************** Generate the tree diagram  *****************\n" +
                "var margin = {top: 20, right: 60, bottom: 20, left: 60},\n" +
                "  width = "+ Math.max(trie.hauteur() * 150 + 300, 1550) +" - margin.right - margin.left,\n" +
                "  height = 650 - margin.top - margin.bottom;\n" +
                "  \n" +
                "var i = 0;\n" +
                "\n" +
                "var tree = d3.layout.tree()\n" +
                "  .size([height, width]);\n" +
                "\n" +
                "var diagonal = d3.svg.diagonal()\n" +
                "  .projection(function(d) { return [d.y, d.x]; });\n" +
                "\n" +
                "var svg = d3.select(\"body\").append(\"svg\")\n" +
                "  .attr(\"width\", width + margin.right + margin.left)\n" +
                "  .attr(\"height\", height + margin.top + margin.bottom)\n" +
                "  .append(\"g\")\n" +
                "  .attr(\"transform\", \"translate(\" + margin.left + \",\" + margin.top + \")\");\n" +
                "\n" +
                "// load the external data\n" +
                "d3.json(\""+ fileName + "_noCollapsible.json" +"\", function(error, treeData) {\n" +
                "  root = treeData[0];\n" +
                "  update(root);\n" +
                "});\n" +
                "\n" +
                "function update(source) {\n" +
                "\n" +
                "  // Compute the new tree layout.\n" +
                "  var nodes = tree.nodes(root).reverse(),\n" +
                "    links = tree.links(nodes);\n" +
                "\n" +
                "  // Normalize for fixed-depth.\n" +
                "  nodes.forEach(function(d) { d.y = d.depth * 150; });\n" +
                "\n" +
                "  // Declare the nodes…\n" +
                "  var node = svg.selectAll(\"g.node\")\n" +
                "    .data(nodes, function(d) { return d.id || (d.id = ++i); });\n" +
                "\n" +
                "  // Enter the nodes.\n" +
                "  var nodeEnter = node.enter().append(\"g\")\n" +
                "    .attr(\"class\", \"node\")\n" +
                "    .attr(\"transform\", function(d) { \n" +
                "      return \"translate(\" + d.y + \",\" + d.x + \")\"; });\n" +
                "\n" +
                "  nodeEnter.append(\"circle\")\n" +
                "    .attr(\"r\", 5)\n" +
                "    .style(\"fill\", \"#fff\");\n" +
                "\n" +
                "  nodeEnter.append(\"text\")\n" +
                "    .attr(\"x\", function(d) { \n" +
                "      return d.children || d._children ? -13 : 13; })\n" +
                "    .attr(\"dy\", \".35em\")\n" +
                "    .attr(\"text-anchor\", function(d) { \n" +
                "      return d.children || d._children ? \"end\" : \"start\"; })\n" +
                "    .text(function(d) { return d.name; })\n" +
                "    .style(\"fill-opacity\", 1);\n" +
                "\n" +
                "  // Declare the links…\n" +
                "  var link = svg.selectAll(\"path.link\")\n" +
                "    .data(links, function(d) { return d.target.id; });\n" +
                "\n" +
                "  // Enter the links.\n" +
                "  link.enter().insert(\"path\", \"g\")\n" +
                "    .attr(\"class\", \"link\")\n" +
                "    .attr(\"d\", diagonal);\n" +
                "\n" +
                "}\n" +
                "\n" +
                "</script>\n" +
                "  \n" +
                "  </body>\n" +
                "</html>\n";
    }

    private static String HtmlCollapsible(Trie trie, String fileName) {
        return
                "<!DOCTYPE html>\n" +
                        "<meta charset=\"utf-8\">\n" +
                        "<style>\n" +
                        "\n" +
                        "body{\n" +
                        "   font: 14px sans-serif;\n" +
                        "}" +
                        "span {\n" +
                        "\n" +
                        "padding-left:150px;\n" +
                        "\n" +
                        "}" +
                        ".node {\n" +
                        "  cursor: pointer;\n" +
                        "}\n" +
                        "\n" +
                        ".node circle {\n" +
                        "  fill: #fff;\n" +
                        "  stroke: steelblue;\n" +
                        "  stroke-width: 1.5px;\n" +
                        "}\n" +
                        "\n" +
                        ".node text {\n" +
                        "  font: 12px sans-serif;\n" +
                        "}\n" +
                        "\n" +
                        ".link {\n" +
                        "  fill: none;\n" +
                        "  stroke: #ccc;\n" +
                        "  stroke-width: 1.5px;\n" +
                        "}\n" +
                        "\n" +
                        "</style>\n" +
                        "<body>\n" +

                        "Nombre de mot : " + trie.comptageMots() + "<span/>" +
                        "Hauteur : " + trie.hauteur() + "<span/>" +
                        "Nombre de pointeurs vers NIL : " + trie.comptageNil() + "<span/>" +
                        "Profondeur moyenne des feuilles : " + trie.profondeurMoyenne() + "<span/>" +
                        "<br/>" +
                        "ListeMots : " + trie.listeMots() + "<br/>" +

                        "<script src=\"http://d3js.org/d3.v3.min.js\"></script>\n" +
                        "<script>\n" +
                        "\n" +
                        "var margin = {top: 20, right: 60, bottom: 20, left: 60},\n" +
                        "    width = " + Math.max(trie.hauteur() * 150 + 300, 1550) + " - margin.right - margin.left,\n" +
                        "    height = 650 - margin.top - margin.bottom;\n" +
                        "\n" +
                        "var i = 0,\n" +
                        "    duration = 300,\n" +
                        "    root;\n" +
                        "\n" +
                        "var tree = d3.layout.tree()\n" +
                        "    .size([height, width]);\n" +
                        "\n" +
                        "var diagonal = d3.svg.diagonal()\n" +
                        "    .projection(function(d) { return [d.y, d.x]; });\n" +
                        "\n" +
                        "var svg = d3.select(\"body\").append(\"svg\")\n" +
                        "    .attr(\"width\", width + margin.right + margin.left)\n" +
                        "    .attr(\"height\", height + margin.top + margin.bottom)\n" +
                        "  .append(\"g\")\n" +
                        "    .attr(\"transform\", \"translate(\" + margin.left + \",\" + margin.top + \")\");\n" +
                        "\n" +
                        "d3.json(\"" + fileName + "_Collapsible.json" + "\", function(error, flare) {\n" +
                        "  if (error) throw error;\n" +
                        "\n" +
                        "  root = flare;\n" +
                        "  root.x0 = height / 2;\n" +
                        "  root.y0 = 0;\n" +
                        "\n" +
                        "  function collapse(d) {\n" +
                        "    if (d.children) {\n" +
                        "      d._children = d.children;\n" +
                        "      d._children.forEach(collapse);\n" +
                        "      d.children = null;\n" +
                        "    }\n" +
                        "  }\n" +
                        "\n" +
                        "  root.children.forEach(collapse);\n" +
                        "  update(root);\n" +
                        "});\n" +
                        "\n" +
                        "d3.select(self.frameElement).style(\"height\", \"800px\");\n" +
                        "\n" +
                        "function update(source) {\n" +
                        "\n" +
                        "  // Compute the new tree layout.\n" +
                        "  var nodes = tree.nodes(root).reverse(),\n" +
                        "      links = tree.links(nodes);\n" +
                        "\n" +
                        "  // Normalize for fixed-depth.\n" +
                        "  nodes.forEach(function(d) { d.y = d.depth * 150; });\n" +
                        "\n" +
                        "  // Update the nodes…\n" +
                        "  var node = svg.selectAll(\"g.node\")\n" +
                        "      .data(nodes, function(d) { return d.id || (d.id = ++i); });\n" +
                        "\n" +
                        "  // Enter any new nodes at the parent's previous position.\n" +
                        "  var nodeEnter = node.enter().append(\"g\")\n" +
                        "      .attr(\"class\", \"node\")\n" +
                        "      .attr(\"transform\", function(d) { return \"translate(\" + source.y0 + \",\" + source.x0 + \")\"; })\n" +
                        "      .on(\"click\", click);\n" +
                        "\n" +
                        "  nodeEnter.append(\"circle\")\n" +
                        "      .attr(\"r\", 1e-6)\n" +
                        "      .style(\"fill\", function(d) { return d._children ? \"lightsteelblue\" : \"#fff\"; });\n" +
                        "\n" +
                        "  nodeEnter.append(\"text\")\n" +
                        "      .attr(\"x\", function(d) { return d.children || d._children ? -13 : 13; })\n" +
                        "      .attr(\"dy\", \".35em\")\n" +
                        "      .attr(\"text-anchor\", function(d) { return d.children || d._children ? \"end\" : \"start\"; })\n" +
                        "      .text(function(d) { return d.name; })\n" +
                        "      .style(\"fill-opacity\", 1e-6);\n" +
                        "\n" +
                        "  // Transition nodes to their new position.\n" +
                        "  var nodeUpdate = node.transition()\n" +
                        "      .duration(duration)\n" +
                        "      .attr(\"transform\", function(d) { return \"translate(\" + d.y + \",\" + d.x + \")\"; });\n" +
                        "\n" +
                        "  nodeUpdate.select(\"circle\")\n" +
                        "      .attr(\"r\", 5)\n" +
                        "      .style(\"fill\", function(d) { return d._children ? \"lightsteelblue\" : \"#fff\"; });\n" +
                        "\n" +
                        "  nodeUpdate.select(\"text\")\n" +
                        "      .style(\"fill-opacity\", 1);\n" +
                        "\n" +
                        "  // Transition exiting nodes to the parent's new position.\n" +
                        "  var nodeExit = node.exit().transition()\n" +
                        "      .duration(duration)\n" +
                        "      .attr(\"transform\", function(d) { return \"translate(\" + source.y + \",\" + source.x + \")\"; })\n" +
                        "      .remove();\n" +
                        "\n" +
                        "  nodeExit.select(\"circle\")\n" +
                        "      .attr(\"r\", 1e-6);\n" +
                        "\n" +
                        "  nodeExit.select(\"text\")\n" +
                        "      .style(\"fill-opacity\", 1e-6);\n" +
                        "\n" +
                        "  // Update the links…\n" +
                        "  var link = svg.selectAll(\"path.link\")\n" +
                        "      .data(links, function(d) { return d.target.id; });\n" +
                        "\n" +
                        "  // Enter any new links at the parent's previous position.\n" +
                        "  link.enter().insert(\"path\", \"g\")\n" +
                        "      .attr(\"class\", \"link\")\n" +
                        "      .attr(\"d\", function(d) {\n" +
                        "        var o = {x: source.x0, y: source.y0};\n" +
                        "        return diagonal({source: o, target: o});\n" +
                        "      });\n" +
                        "\n" +
                        "  // Transition links to their new position.\n" +
                        "  link.transition()\n" +
                        "      .duration(duration)\n" +
                        "      .attr(\"d\", diagonal);\n" +
                        "\n" +
                        "  // Transition exiting nodes to the parent's new position.\n" +
                        "  link.exit().transition()\n" +
                        "      .duration(duration)\n" +
                        "      .attr(\"d\", function(d) {\n" +
                        "        var o = {x: source.x, y: source.y};\n" +
                        "        return diagonal({source: o, target: o});\n" +
                        "      })\n" +
                        "      .remove();\n" +
                        "\n" +
                        "  // Stash the old positions for transition.\n" +
                        "  nodes.forEach(function(d) {\n" +
                        "    d.x0 = d.x;\n" +
                        "    d.y0 = d.y;\n" +
                        "  });\n" +
                        "}\n" +
                        "\n" +
                        "// Toggle children on click.\n" +
                        "function click(d) {\n" +
                        "  if (d.children) {\n" +
                        "    d._children = d.children;\n" +
                        "    d.children = null;\n" +
                        "  } else {\n" +
                        "    d.children = d._children;\n" +
                        "    d._children = null;\n" +
                        "  }\n" +
                        "  update(d);\n" +
                        "}\n" +
                        "\n" +
                        "</script>\n" +
                        "</body>"
                ;
    }
}
