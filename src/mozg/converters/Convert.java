/*
 * Copyright (C) 2011 Mozgin Dmitry
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package mozg.converters;

import java.io.File;

/**
 * Created: Wed May 11 17:36:32 2011
 *
 * @author m039
 * @version 1.0
 */
public interface Convert {

    /**
     * Implementation can convert a file from one format to another.
     * 
     * @f a file to be converted
     */
    public void convert(File f);
}
