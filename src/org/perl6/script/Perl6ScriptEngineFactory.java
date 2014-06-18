package org.perl6.script;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;

public class Perl6ScriptEngineFactory implements ScriptEngineFactory {

    public static final String PERL6 = "perl6";
    public static final String ENGINE_VERSION = "1.0";
    public static final String LANGUAGE_VERSION = "6";

    @Override
    public String getEngineName() {
        return PERL6;
    }

    @Override
    public String getEngineVersion() {
        return ENGINE_VERSION;
    }

    public static final List<String> extensions = Collections.unmodifiableList(Arrays.asList(new String[] {
            "pl", "p6", "pm", "pm6" }));

    @Override
    public List<String> getExtensions() {
        return extensions;
    }

    public static final List<String> mimeTypes = Collections.unmodifiableList(new ArrayList<String>());

    @Override
    public List<String> getMimeTypes() {
        return mimeTypes;
    }

    public static final List<String> names = Collections.unmodifiableList(Arrays.asList(new String[] {
            "Perl6", "perl6" }));

    @Override
    public List<String> getNames() {
        return names;
    }

    @Override
    public String getLanguageName() {
        return PERL6;
    }

    @Override
    public String getLanguageVersion() {
        return LANGUAGE_VERSION;
    }

    @Override
    public Object getParameter(String key) {
        if (key.equals(ScriptEngine.NAME)) {
            return getLanguageName();
        } else if (key.equals(ScriptEngine.ENGINE)) {
            return getEngineName();
        } else if (key.equals(ScriptEngine.ENGINE_VERSION)) {
            return getEngineVersion();
        } else if (key.equals(ScriptEngine.LANGUAGE)) {
            return getLanguageName();
        } else if (key.equals(ScriptEngine.LANGUAGE_VERSION)) {
            return getLanguageVersion();
        } else if (key.equals("THREADING")) {
            return "MULTITHREADED";
        } else {
            return null;
        }
    }

    @Override
    public String getMethodCallSyntax(String obj, String m, String... args) {
        StringBuilder buf = new StringBuilder();
        buf.append(obj).append(".").append(m).append("(");
        for (int i = 0; i < args.length; i++) {
            buf.append(args[i]);
            if (i < args.length - 1) {
                buf.append(", ");
            }
        }
        buf.append(")");
        return buf.toString();
    }

    @Override
    public String getOutputStatement(String toDisplay) {
        return "say " + toDisplay;
    }

    @Override
    public String getProgram(String... statements) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < statements.length; i++) {
            buf.append(statements[i]);
            buf.append("\n");
        }
        return buf.toString();
    }

    @Override
    public ScriptEngine getScriptEngine() {
        return new Perl6ScriptEngine();
    }

}
