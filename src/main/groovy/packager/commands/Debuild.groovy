/*
 * Copyright 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package packager.commands

import org.slf4j.LoggerFactory
import org.slf4j.Logger


final class Debuild implements Command {

    private static final Logger logger = LoggerFactory.getLogger(Debuild)

    private String script = getClass().getResourceAsStream('/packager/debuild/debuild.sh').text
    private final File target

    Debuild(File target) {
        this.target = target
    }

    @Override void execute() {
        File debuild = installBuildScript()

        def command = "$debuild.absolutePath $target.absolutePath"
        def process = command.execute([], target)

        def sout = new StringBuffer()
        def serr = new StringBuffer()
        process.consumeProcessOutput(sout, serr)
        assert process.waitFor() == 0: serr.toString()
        logger.debug("$command ${sout.toString()}")
    }

    private File installBuildScript() {
        def debuild = new File(target, 'debuild.sh')
        debuild.text = script
        debuild.executable = true
        return debuild
    }

    boolean equals(o) {
        if(this.is(o)) return true;
        if(getClass() != o.class) return false;

        Debuild debuild = (Debuild) o;

        if(script != debuild.script) return false;
        if(target != debuild.target) return false;

        return true;
    }

    int hashCode() {
        int result;
        result = (script != null ? script.hashCode() : 0);
        result = 31 * result + (target != null ? target.hashCode() : 0);
        return result;
    }

    public String toString() {
        return "Debuild{" +
                "script='" + script + '\'' +
                ", target=" + target +
                '}';
    }
}