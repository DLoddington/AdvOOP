
public class Address {

		private String housenum; //keep as string because some addresses are e.g. Flat 8 or 105a
		private String street;
		private String towncity;
		private String country;
		private String postcode;
		
		public Address(String housenum, String street, String towncity, String country, String postcode) {

			this.housenum = housenum;
			this.street = street;
			this.towncity = towncity;
			this.country = country;
			this.postcode = postcode;
		}
		
		public String getHousenum() {
			return housenum;
		}

		public void setHousenum(String housenum) {
			this.housenum = housenum;
		}

		public String getStreet() {
			return street;
		}

		public void setStreet(String street) {
			this.street = street;
		}

		public String getTowncity() {
			return towncity;
		}

		public void setTowncity(String towncity) {
			this.towncity = towncity;
		}

		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
		}

		public String getPostcode() {
			return postcode;
		}

		public void setPostcode(String postcode) {
			this.postcode = postcode;
		}

		@Override
		public String toString() {
			return housenum + " " + street + ", " + towncity
					+ ", " + country + ", " + postcode;
		}
	
}
