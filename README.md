# JATS to LaTeX
Java project that can be used for transformation of Journal Article Tag Suite XML to LaTeX

## What it does?
JATS2LaTeX accepts JATS XML as input and produces document in LaTeX format and also a bibtex file for bibliography.

## How to use?
1. Downlaod the latest release: https://github.com/Vitaliy-1/JATS2LaTeX/releases
2. Run as java jar executable file. For example, if jar and article file are both lie in D:\latex, just type from Windows cmd (win + r -> cmd): `D:` -> `cd latex` -> `java -jar latex.jar article.xml article.tex article.bib`, where latex.jar is a path to the jar file, article.xml - input JATS XML file and article.tex and bib - output latex files.

## Examples
Examples can be seen in the examples folder. Also all pdf on this [site](https://e-medjournal.com) are made with JATS2LaTeX: 

## Requirement
- App works with [Java 8](https://java.com/en/download/)
- For compiling there is a need of a standard TeX/LaTeX distribution.  For Windows I prefer [MikTeX](https://miktex.org/)
- Any LaTeX editor
- Compile with **_XeLaTeX_**

## Features
- UTF-8 support.
- Parsing of sections, subsections and subsubsections. Italic and bold text are supported. Intext links to bibliography, tables and figure are also supported. LaTeX special symbols are replaced. If I forgot about a symbol, that need to be replaced - please let me know with opening an issue. It can be fixed in no-time.
- Parsing of figures. Moreover it tries to download the figure if it has valid url link to the file inside JATS.
- Parsing of Tables. Because tables in XML and LaTeX are very different, the problems may occur in complex tables. For example, if in a one row article has multicolumn followed with multirow parser may place "&" symbol in not appropriate place in the next row. I am planing to fix this in a future release. Nevertheless it takes several seconds to fix if ones knows latex syntax.
- Parsing of reference list. As I noted earlier, converter produces reference list in bib format. As I know, bib file is most extensively used format for bibliography exchange, moreover, with bib reference list in LaTeX can be styled in any wide used reference styles (Vancouver, APA etc.). The program supports references to journals, books, chapters and conferences. We prefer to code the in JATS element-citation element, like: <element-citation publication-type="journal">. You can find our JATS in examples folder (articles are copyrighted by authors). If citation type is not explicitly pointed, the parser will look at the used tags inside element-citation element.
