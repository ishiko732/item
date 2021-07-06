library ieee;
use ieee.std_logic_1164.all;
use ieee.std_logic_unsigned.all;

entity Dff_8bit is
port(
	D:in std_logic_vector(7 downto 0);
	clk:in std_logic;
	R:out std_logic_vector(7 downto 0)
);
end Dff_8bit;

architecture bhv of Dff_8bit is
begin
	process(clk)
	begin
		if (clk'event and clk='1') then
			R<=D;
		end if;
	end process;
end bhv;