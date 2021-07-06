library ieee;
use ieee.std_logic_1164.all;
use ieee.std_logic_unsigned.all;
entity mux21 is
port(
	R0,R1:in std_logic_vector(3 downto 0);
				 S:in std_logic;
				 R:out std_logic_vector(3 downto 0)
);
end mux21;

architecture bhv of mux21 is
begin
	 process(S)
	 begin
		case S is
			when '0'=>R<=R0;
			when '1'=>R<=R1;
		end case;
	end process;
end bhv;
		