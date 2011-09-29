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


final class Extractor implements Command {

    private static final Logger logger = LoggerFactory.getLogger(Extractor)

    private final File archive
    private final File to

    Extractor(File archive, File to) {
        this.archive = archive
        this.to = to
    }

    void execute() {
        to.mkdirs()
        def command = "tar -x -z -C $to.absolutePath -f $archive.absolutePath"
        def process = command.execute()
        assert process.waitFor() == 0 : process.err.text
        logger.debug("extracting...\n$command\n${process.err.text}")
    }

    public String toString() {
        return "Extractor{" +
                "archive=" + archive +
                ", to=" + to +
                '}';
    }

    boolean equals(o) {
        if(this.is(o)) return true;
        if(getClass() != o.class) return false;

        Extractor extractor = (Extractor) o;

        if(archive != extractor.archive) return false;
        if(to != extractor.to) return false;

        return true;
    }

    int hashCode() {
        int result;
        result = (archive != null ? archive.hashCode() : 0);
        result = 31 * result + (to != null ? to.hashCode() : 0);
        return result;
    }
}