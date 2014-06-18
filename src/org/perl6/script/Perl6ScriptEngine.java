package org.perl6.script;

import java.io.PrintStream;
import java.io.Reader;

import javax.script.AbstractScriptEngine;
import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptException;

import org.apache.commons.io.input.ReaderInputStream;
import org.apache.commons.io.output.WriterOutputStream;
import org.perl6.eclipse.Activator;
import org.perl6.nqp.runtime.CodeRef;
import org.perl6.nqp.runtime.CompilationUnit;
import org.perl6.nqp.runtime.GlobalContext;
import org.perl6.nqp.runtime.LibraryLoader;
import org.perl6.nqp.runtime.Ops;

public class Perl6ScriptEngine extends AbstractScriptEngine {

    public Perl6ScriptEngine() {
    }

    @Override
    public Object eval(String script, ScriptContext context) throws ScriptException {
        try {
            GlobalContext gc = new GlobalContext();
            gc.in = new ReaderInputStream(context.getReader());
            gc.out = new PrintStream(new WriterOutputStream(context.getWriter()), true);
            gc.err = new PrintStream(new WriterOutputStream(context.getErrorWriter()), true);
            gc.interceptExit = true;
            gc.sharingHint = true;

            Class<?> cuType = LibraryLoader.loadFile(Activator.getPrefix()
                    + "/languages/perl6/runtime/perl6.jar", true);
            CompilationUnit cu = CompilationUnit.setupCompilationUnit(gc.mainThread, cuType, true);
            CodeRef entryRef = null;
            if (cu.entryQbid() >= 0)
                entryRef = cu.lookupCodeRef(cu.entryQbid());
            if (entryRef == null)
                throw new RuntimeException("This class is not an entry point");

            Ops.invokeMain(gc.mainThread, entryRef, cuType.getName(), new String[] { "--ll-exception",
                    "-e", script });
            return null;
        } catch (Exception e) {
            throw new ScriptException(e);
        }
    }

    @Override
    public Object eval(Reader reader, ScriptContext context) throws ScriptException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Bindings createBindings() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ScriptEngineFactory getFactory() {
        // TODO Auto-generated method stub
        return null;
    }

}
