package it.unibo.xtextIntro20.generator
import org.eclipse.xtext.generator.IFileSystemAccess
 

class GenUtils {
 public static IFileSystemAccess curFsa
 public val static final logo =  "/* Generated by AN DISI Unibo */ "
 public var static packageName = ""
 public var static fileExtenstion     = null;
 
 public var static String pubSubServAddr = null;
 
 def static setFileExtension( String fe ){
    fileExtenstion = fe
 } 
 
 def static setPbSubServer(String addr){
 	pubSubServAddr = addr;
 }
 
 def static setPackageName(String applName){
 	packageName = "it.unibo."+applName; 
 }
  def static setFsa( IFileSystemAccess fsa ){
    curFsa = fsa
 }
 
 /* 
 * Utility to generate a file
 */ 
def static genFile( String packageName, String name, String suffix, CharSequence contents){
 var fName   = packageName.replace(".", "/") + "/" + name + "." + suffix
 curFsa.generateFile( fName,contents ) 
}	
def static genFileRoot( String packageName, String name, String suffix, CharSequence contents){
 var sysName = packageName.replace(".", "/")
 var fName   = "../"+sysName + "/" + name + "." + suffix
 curFsa.generateFile( fName,contents ) 
}
//dir ="../src" is the source 
def static genFileDir( String dir, String packageName, String name, String suffix, CharSequence contents){
 var sysName = packageName.replace(".", "/")
 var fName = ""
 if( sysName.length > 0 )
 	fName   = dir+"/"+sysName + "/" + name + "." + suffix
 else 
 	fName   = dir+"/"+ name + "." + suffix
// System.out.println(" +++ generate " + fName);
 curFsa.generateFile( fName,contents ) 
}
def static genFileSrcDir( String packageName, String name, String suffix, CharSequence contents){
	System.out.println(" +++++ generate " + name + " packageName=" + packageName);
 var sysName = packageName.replace(".", "/")
 var fName   = ""
 
 if( sysName.length > 0 )
 	fName   =  "../src/"+sysName + "/" + name + "." + suffix
 else 
 	fName   =  "../src/" + name + "." + suffix
	System.out.println(" +++++ generate " + fName);
 	curFsa.generateFile( fName,contents ) 
}

 

}