package com.veradat.nodemanager.domain.model.match;

import com.veradat.nodemanager.domain.model.Mapping;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@Data
public class NodeMappings implements Serializable {

   @Serial
   private static final long serialVersionUID = -5159524849610386144L;

   private List<Mapping> mappings;

}
