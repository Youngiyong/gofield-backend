package com.gofield.common.utils.type;

import com.gofield.common.utils.type.ImageType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ImageTypeTest {

    @Test
    void 파일_이름을_입력하면_지정된_디렉터리와_함께_파일_이름이_반환된다() {
        // given
        String fileName = "주황버섯.png";
        ImageType type = ImageType.STORE_IMAGE;

        // when
        String result = type.getFileNameWithDirectory(fileName);

        // then
        assertThat(result).isEqualTo("store/주황버섯.png");

    }

}
