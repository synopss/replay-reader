package com.synops.replayreader.update;

import com.fasterxml.jackson.annotation.JsonProperty;

public record VersionResponse(@JsonProperty("tag_name") String tagName) {

}
