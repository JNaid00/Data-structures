JFLAGS = -g
JC = javac
JVM= java
.SUFFIXES: .java .class
.java.class:
        $(JC) $(JFLAGS) $*.java
# Leave every thing above this alone

#Replace al the files below this with your files in the classes = 
CLASSES = \
        MAIN.java \
        SkipList.java \
        SkipListNode.java

#leave everything below this alone
MAIN = Main
default: classes

classes: $(CLASSES:.java=.class)

run: $(MAIN).class
	$(JVM) $(MAIN) $(FILE)

clean:
        $(RM) *.class