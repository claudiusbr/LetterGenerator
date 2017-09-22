# LetterGenerator
A program to generate individually addressed letters.


### How it works
This program takes a Docx template for a letter, a CSV file and a destination
folder, and it generates individual letters based on the details of the details
and template.

The template should contain variable names in the `${name}` notation in its
body, e.g.:

```
Dear ${some name},

My name is ${my name}.
You ${action}.
Prepare to ${consequence}.
```

The Details file should contain column names matching the variables in the
template. E.g.:
```
some name,my name,action,consequence
Six Fingered Man,Inigo Montoya,killed my father,prepare to die
Quick Brown Fox,Lazy Dog,jumped over me,have me jump over you
```

This should generate individual docx files with the contents replaced by those of the template. E.g.:
```
Dear Six Fingered Man,		|	Dear Quick Brown Fox,
							|
My name is Inigo Montoya.	|	My name is Lazy Dog.
You killed my father.		|	You jumped over me.
Prepare to die.				|	Prepare to have me jump over you.
```


### Acknowledgements
This software uses the [docx4j library](https://www.docx4java.org/trac/docx4j), which uses the [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0). Their legal information can be found on the [docx4j GitHub repo](https://github.com/plutext/docx4j#legal-information).