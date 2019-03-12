/*
 * Copyright 2019 ConsenSys AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package tech.pegasys.pantheon.services.pipeline;

import java.util.Iterator;
import java.util.function.Function;
import java.util.stream.Stream;

class FlatMapProcessor<I, O> implements Processor<I, O> {

  private final Function<I, Stream<O>> mapper;

  public FlatMapProcessor(final Function<I, Stream<O>> mapper) {
    this.mapper = mapper;
  }

  @Override
  public void processNextInput(final ReadPipe<I> inputPipe, final WritePipe<O> outputPipe) {
    final I value = inputPipe.get();
    if (value != null) {
      final Iterator<O> outputs = mapper.apply(value).iterator();
      while (outputs.hasNext()) {
        outputPipe.put(outputs.next());
      }
    }
  }
}
