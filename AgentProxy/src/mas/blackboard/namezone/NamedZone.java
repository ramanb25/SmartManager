package mas.blackboard.namezone;

import jade.core.AID;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
/**
 * Creates zone with name
 * @author NikhilChilwant
 *
 */
public class NamedZone implements ZoneName
{
   private AID name = null;

   /**
    * 
    * @param name Name of zone
    */
   public NamedZone(AID name) {
      Validate.notNull(name);
      this.name = name;
   }

   public String name() {
      return this.name.getName();
   }

   public boolean equals(Object obj) {
	   if(obj instanceof NamedZone){
	        final NamedZone other = (NamedZone) obj;
	        return new EqualsBuilder()
	            .append(name, other.name)
	            .isEquals();
	    } else {
	        return false;
	    }
   }

   public int hashCode() {
      return new HashCodeBuilder()
      			.append(this.name)
  				.append(NamedZone.class)
  				.toHashCode();
   }

   public String toString() {
      return "a NamedZone \"" + this.name() + "\"";
   }
}
