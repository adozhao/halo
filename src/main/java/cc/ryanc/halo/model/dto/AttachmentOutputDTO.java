package cc.ryanc.halo.model.dto;

import cc.ryanc.halo.model.dto.base.OutputConverter;
import cc.ryanc.halo.model.entity.Attachment;
import lombok.Data;

/**
 * Attachment output dto.
 *
 * @author johnniang
 * @date 3/21/19
 */
@Data
public class AttachmentOutputDTO implements OutputConverter<AttachmentOutputDTO, Attachment> {

    private Integer id;

    private String name;

    private String path;

    private String thumbPath;

    private String mediaType;

    private String suffix;

    private String dimension;

    private String size;

    private Integer type;
}