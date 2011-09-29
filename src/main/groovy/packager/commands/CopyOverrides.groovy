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


final class CopyOverrides implements Command {

    private static final Logger logger = LoggerFactory.getLogger(CopyOverrides)

    private final File source
    private final File target

    CopyOverrides(File source, File target) {
        this.source = source
        this.target = target
    }

    @Override void execute() {
        withExistingSourceDir {
            withTargetDir {
                source.listFiles().each {File f ->
                    def command = "cp -Rf $f.absolutePath $target.absolutePath".toString()
                    logger.debug(command)
                    def process = command.execute()
                    def result = process.waitFor()
                    assert result == 0: "Failed to copy overrides! [${process.err.text.replaceAll(/[\n\r]*/, '')}]"
                }
            }
        }
    }

    private withExistingSourceDir(c) {
        if(source.exists()) {
            assert source.directory
            c()
        }
    }

    private withTargetDir(c) {
        if(!target.exists()) target.mkdirs()
        assert target.directory
        c()
    }

    boolean equals(o) {
        if(this.is(o)) return true;
        if(getClass() != o.class) return false;

        CopyOverrides that = (CopyOverrides) o;

        if(source != that.source) return false;
        if(target != that.target) return false;

        return true;
    }

    int hashCode() {
        int result;
        result = (source != null ? source.hashCode() : 0);
        result = 31 * result + (target != null ? target.hashCode() : 0);
        return result;
    }

    public String toString() {
        return "CopyOverrides{" +
                "source=" + source +
                ", target=" + target +
                '}';
    }
}
