library ieee;
use ieee.std_logic_1164.all;
use ieee.std_logic_unsigned.all;
entity mux21_S is
port(
	R0,R1:in std_logic_vector(1 downto 0);
				 S:in std_logic;
				 R:out std_logic_vector(1 downto 0)
);
end mux21_S;

architecture bhv of mux21_S is
begin
	 process(S)
	 begin
		case S is
			when '0'=>R<=R0;
			when '1'=>R<=R1;
		end case;
	end process;
end bhv;
		